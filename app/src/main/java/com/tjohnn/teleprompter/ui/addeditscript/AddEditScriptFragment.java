package com.tjohnn.teleprompter.ui.addeditscript;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerFragmentX;
import com.tjohnn.teleprompter.data.Script;
import com.tjohnn.teleprompter.utils.DateUtils;
import com.tjohnn.teleprompter.utils.FileUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class AddEditScriptFragment extends DaggerFragmentX {

    private int PICK_FILE_RESULT_CODE = 45;
    public static final String SCRIPT_ID_KEY = "SCRIPT_ID_KEY";

    private static final String MIME_TYPE_PLAIN_TEXT = "text/plain";

    private long mScriptId;

    @Inject
    AppCompatActivity mActivity;
    @Inject
    AddEditScriptViewModel mViewModel;

    @BindView(R.id.et_title)
    EditText title;
    @BindView(R.id.et_script_text)
    EditText scriptText;
    @BindView(R.id.tv_teleprompt_date)
    TextView telepromptDate;
    @BindView(R.id.tv_teleprompt_time)
    TextView telepromptTime;
    @BindView(R.id.tv_error)
    TextView errorView;


    private Calendar telepromptCalendar;

    private DatePickerDialog.OnDateSetListener mDateListener = (view, year, monthOfYear, dayOfMonth) -> {
        telepromptCalendar.set(Calendar.YEAR, year);
        telepromptCalendar.set(Calendar.MONTH, monthOfYear);
        telepromptCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        telepromptDate.setText(DateUtils.calendarToDateFormat(telepromptCalendar));
    };

    private TimePickerDialog.OnTimeSetListener mTimeListener = (timePicker, hour, minute) -> {
        telepromptCalendar.set(Calendar.HOUR_OF_DAY, hour);
        telepromptCalendar.set(Calendar.MINUTE, minute);
        telepromptTime.setText(DateUtils.calendarToTime12(telepromptCalendar));
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null){
            mScriptId = getArguments().getLong(SCRIPT_ID_KEY, 0);
        } else if (savedInstanceState != null) {
            mScriptId = savedInstanceState.getLong(SCRIPT_ID_KEY, 0);
        }

        if(mScriptId > 0){
            mViewModel.loadScript(mScriptId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_script, container, false);
        ButterKnife.bind(this, view);
        subscribeToViewModel();
        return view;
    }

    private void subscribeToViewModel() {
        mViewModel.getFormError().observe(this, r -> {
            errorView.setText(r.getContentIfNotUsed());
        });

        mViewModel.getSnackBarMessage().observe(this, r -> {
            if(r != null && r.getContentIfNotUsed() != null){
                Snackbar.make(getView(), r.peekContent(), Snackbar.LENGTH_LONG).show();
            }
        });

        mViewModel.getScript().observe(this, script -> {
            if(script != null ){
                title.setText(script.getTitle());
                scriptText.setText(script.getText());
                telepromptCalendar = Calendar.getInstance();
                telepromptCalendar.setTime(script.getTelepromptingDate());

                telepromptDate.setText(DateUtils.calendarToDateFormat(telepromptCalendar));
                telepromptTime.setText(DateUtils.calendarToTime12(telepromptCalendar));
            }
        });

        mViewModel.getOnScriptAdded().observe(this, r -> {
            if(r != null && r.getContentIfNotUsed() != null && r.peekContent()){
                Toast.makeText(mActivity, R.string.script_saved, Toast.LENGTH_LONG).show();
                mActivity.onBackPressed();
            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(SCRIPT_ID_KEY, mScriptId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        telepromptTime.setOnClickListener(v -> {
            if(telepromptCalendar == null) {
                Toast.makeText(mActivity, R.string.select_date_before_time, Toast.LENGTH_SHORT).show();
                return;
            }
            new TimePickerDialog(mActivity, mTimeListener,
                    telepromptCalendar.get(Calendar.HOUR),
                    telepromptCalendar.get(Calendar.MINUTE), false
            ).show();
        });

        telepromptDate.setOnClickListener(v -> {
                    if(telepromptCalendar == null) telepromptCalendar = Calendar.getInstance();
                    new DatePickerDialog(mActivity, mDateListener,
                            telepromptCalendar.get(Calendar.YEAR), telepromptCalendar.get(Calendar.MONTH),
                            telepromptCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show();
                }
            );

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_script, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_import_script){
            selectTextFile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_FILE_RESULT_CODE && data != null && data.getData() != null){
            ContentResolver resolver = mActivity.getContentResolver();

            Uri uri = data.getData();

            String type = FileUtils.getMimeType(mActivity, uri);

            // validate file type
            if(!MIME_TYPE_PLAIN_TEXT.equals(type)){
                Toast.makeText(mActivity, R.string.invalid_file_type_selected, Toast.LENGTH_LONG).show();
                return;
            }

            String fileName = FileUtils.getFileDisplayName(mActivity, uri);

            if(fileName != null){
                String extension = FileUtils.getExtension(mActivity, uri);
                fileName = fileName.replaceAll("\\." +extension + "$", "");
                title.setText(fileName);
            }


            StringBuilder builder = new StringBuilder();
            try {
                byte[] buffer = new byte[1024];

                InputStream inputStream = resolver.openInputStream(data.getData());

                while(inputStream.read(buffer) != -1) {
                    builder.append(new String(buffer));
                }

                // Always close files.
                inputStream.close();

                scriptText.setText(builder.toString());
            }
            catch(FileNotFoundException|NullPointerException ex) {
                Timber.d("Unable to open file: " + fileName);
            }
            catch(IOException ex) {
                Timber.d("Error reading file: " + fileName);
            }

        }
        else {
            Toast.makeText(mActivity, R.string.unable_to_read_selected_file, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_submit)
    void submit(){
        Script script = mViewModel.getScript().getValue();
        if(script == null) {
            script = new Script(
                    title.getText().toString(),
                    scriptText.getText().toString(),
                    telepromptCalendar == null ? null : telepromptCalendar.getTime(),
                    false,
                    new Date()
            );
        } else {
            script.setTitle(title.getText().toString());
            script.setText(scriptText.getText().toString());
            script.setTelepromptingDate(telepromptCalendar == null ? null : telepromptCalendar.getTime());
        }

        mViewModel.saveScript(script);
    }

    private void selectTextFile(){

        String[] mimeTypes = {MIME_TYPE_PLAIN_TEXT};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes[0]);
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        } else {
            StringBuilder mimeTypesStr = new StringBuilder();
            for (String mimeType : mimeTypes) {
                mimeTypesStr.append(mimeType).append("|");
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"Import Text File"), PICK_FILE_RESULT_CODE);

    }
}
