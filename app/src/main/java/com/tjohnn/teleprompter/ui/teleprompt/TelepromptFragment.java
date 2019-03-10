package com.tjohnn.teleprompter.ui.teleprompt;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tjohnn.teleprompter.R;
import com.tjohnn.teleprompter.daggerjetifier.DaggerFragmentX;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TelepromptFragment extends DaggerFragmentX {

    public static final String SCRIPT_ID_KEY = "SCRIPT_ID_KEY";
    public static final int MIN_SCROLL_DURATION_MULTIPLIER = 10; // fastest multiplier
    public static final int MAX_SCROLL_DURATION_MULTIPLIER = 26; // slowest multiplier
    private long mScriptId;

    @Inject
    TelepromptViewModel mViewModel;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = inflater.inflate(R.layout.fragment_teleprompt, container, false);
        ButterKnife.bind(this, view);

        subscribeToViewModel();
        return view;
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
    }

    private void subscribeToViewModel() {
        mViewModel.getScript().observe(this, script -> {
            if(script != null){
                scriptText.setText(script.getText());
                scriptText.post(this::animateScroll);
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(SCRIPT_ID_KEY, mScriptId);
        super.onSaveInstanceState(outState);
    }


    private void animateScroll() {

        int x = 0;

        int speed = seekBar.getProgress();

        // we have the slower scroll speed with a higher value of speed multiplier
        // if speed form seekbar is 0, then we have (26 - 0) as multiplier,
        // if speed = 16, the we have (26 - 16) which is the fastest etc
        int speedMultiplier = MAX_SCROLL_DURATION_MULTIPLIER - speed;

        // get the remaining size-y of the view needed to be scrolled
        int bottom = scriptText.getBottom() + mScrollView.getPaddingBottom();
        int scrollPositionY = mScrollView.getScrollY();
        int scrollHeight = mScrollView.getHeight();
        int y = bottom - (scrollPositionY + scrollHeight);

        //long duration = y * 26; // slowest
        long duration = y * speedMultiplier;

        Timber.d("Bottom is %s, scrollPositionY is %s, scrollHeight is %s, Ati y gangan: %s",
                bottom, scrollPositionY, scrollHeight, y);



        ObjectAnimator xTranslate = ObjectAnimator.ofInt(mScrollView, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(mScrollView, "scrollY", y);


        mAnimator = new AnimatorSet();

        mAnimator.setDuration(duration);
        mAnimator.playTogether(xTranslate, yTranslate);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        mAnimator.start();

    }


}
