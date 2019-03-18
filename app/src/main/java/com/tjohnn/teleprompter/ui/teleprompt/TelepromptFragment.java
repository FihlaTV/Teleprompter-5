package com.tjohnn.teleprompter.ui.teleprompt;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.tjohnn.teleprompter.AppWidgetIntentService;
import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerFragmentX;
import com.tjohnn.teleprompter.data.PreferenceHelper;
import com.tjohnn.teleprompter.ui.settings.SettingsActivity;
import com.tjohnn.teleprompter.utils.TimberTree;

import java.util.Timer;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TelepromptFragment extends DaggerFragmentX {

    public static final String SCRIPT_ID_KEY = "SCRIPT_ID_KEY";
    private static final String SCROLL_POSITION_KEY = "SCROLL_POSITION_KEY";
    public static final int MIN_SCROLL_DURATION_MULTIPLIER = 10; // fastest multiplier
    private static final int MAX_SCROLL_DURATION_MULTIPLIER = 26; // slowest multiplier
    private long mScriptId;

    @Inject
    TelepromptViewModel mViewModel;
    @Inject
    AppCompatActivity mActivity;
    @Inject
    PreferenceHelper preferenceHelper;

    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.tv_script_text)
    TextView scriptText;
    @BindView(R.id.seekbar)
    SeekBar seekBar;
    @BindView(R.id.btn_pause_play)
    ImageButton pausePlay;
    @BindView(R.id.btn_stop)
    ImageButton stop;


    private AnimatorSet mAnimator;
    private double scrollPosition;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mScriptId = getArguments().getLong(SCRIPT_ID_KEY, 0);
        }
        if (savedInstanceState != null) {
            Timber.d("savedInstanceState: " + savedInstanceState.toString());
            scrollPosition = savedInstanceState.getDouble(SCROLL_POSITION_KEY, 0);

            mScriptId = savedInstanceState.getLong(SCRIPT_ID_KEY, 0);
        }

        if(mScriptId > 0){
            mViewModel.loadScript(mScriptId);
        }

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teleprompt, container, false);
        ButterKnife.bind(this, view);

        subscribeToViewModel();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_teleprompt, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            startActivity(new Intent(mActivity, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mAnimator.cancel();
                    animateScroll();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pausePlay.setOnClickListener(v -> {
            if(mAnimator != null && mAnimator.isRunning()){
                mAnimator.cancel();

            } else if(!mScrollView.canScrollVertically(1)){
                mScrollView.smoothScrollTo(0, 0);

                // allow ui to finish scrolling to top before starting animation
                new Handler().postDelayed(() -> mActivity.runOnUiThread(this::animateScroll), 600);
            } else {
                animateScroll();
            }
        });

        stop.setOnClickListener(v -> {
            mAnimator.cancel();
            mScrollView.smoothScrollTo(0, 0);
        });

        // listen to when animation scroll ends
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if(!mScrollView.canScrollVertically(1)){
                // mark as teleprompted when finished
                mViewModel.markComplete();

                // animation is not stopping itself when scrolling complete
                // so stopping is done manually, need to look into this later
                if(mAnimator != null) {
                    mAnimator.cancel();
                }


                pausePlay.setImageResource(R.drawable.ic_play_arrow_orange_24dp);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // set theme from preferences
        int fontSize = preferenceHelper.getTelepromptTextSize();
        scriptText.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        String theme = preferenceHelper.getTelepromptTheme();
        if(getString(R.string.pref_theme_light).equals(theme)){
            scriptText.setBackgroundColor(Color.WHITE);
            scriptText.setTextColor(Color.BLACK);
        } else if(getString(R.string.pref_theme_dark).equals(theme)) {
            scriptText.setBackgroundColor(Color.BLACK);
            scriptText.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mAnimator != null){
            mAnimator.cancel();
        }
    }

    private void subscribeToViewModel() {
        mViewModel.getScript().observe(this, script -> {
            if(script != null){
                scriptText.setText(script.getText());

                // set scroll position to the one saved the database if current is less than or equals to zero
                scrollPosition = scrollPosition <= 0 ? script.getScrollPosition() : scrollPosition;
                scriptText.post(() -> {
                    int pos = new Double(scriptText.getHeight() * scrollPosition).intValue();
                    mScrollView.scrollTo(0, pos);
                });
            }
        });

        mViewModel.getSnackBarMessage().observe(this, r -> {
            if(r != null && r.getContentIfNotUsed() != null){
                Snackbar.make(getView(), r.peekContent(), Snackbar.LENGTH_LONG).show();
            }
        });

        mViewModel.getMarkedComplete().observe(this, r -> {
            if(r != null && r.getContentIfNotUsed() != null && r.peekContent()){
                // update widget
                AppWidgetIntentService.startActionUpdateScriptWidgets(mActivity.getApplicationContext());
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(SCRIPT_ID_KEY, mScriptId);



        outState.putDouble(SCROLL_POSITION_KEY, getScrollingPositionPercent());

        super.onSaveInstanceState(outState);
    }

    private float getScrollingPositionPercent(){
        // it is okay to save the percent for accurate repositioning of scroll bar
        // due to the difference in screen size of a device for different orientations
        int pos = mScrollView.getScrollY();
        return pos * 1.0f / scriptText.getHeight();
    }


    @Override
    public void onDestroy() {
        mViewModel.saveScrollingPosition(getScrollingPositionPercent());
        super.onDestroy();
    }


    private void animateScroll() {

        int x = 0;

        int speed = seekBar.getProgress();

        // we have the slower scroll speed with a higher value of speed multiplier
        // if speed form seek bar is 0, then we have (26 - 0) as multiplier,
        // if speed = 16, the we have (26 - 16) which is the fastest etc
        int speedMultiplier = MAX_SCROLL_DURATION_MULTIPLIER - speed;

        // we are scrolling to the bottom of #scriptText
        int y = scriptText.getBottom() + mScrollView.getPaddingBottom();

        //long duration = y * 26; // slowest
        long duration = y * speedMultiplier;

        ObjectAnimator xTranslate = ObjectAnimator.ofInt(mScrollView, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(mScrollView, "scrollY", y);

        mAnimator = new AnimatorSet();

        mAnimator.setDuration(duration);
        mAnimator.playTogether(xTranslate, yTranslate);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                pausePlay.setImageResource(R.drawable.ic_pause_orange_24dp);
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationEnd(Animator arg0) {

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                pausePlay.setImageResource(R.drawable.ic_play_arrow_orange_24dp);
            }
        });
        mAnimator.start();

    }


}
