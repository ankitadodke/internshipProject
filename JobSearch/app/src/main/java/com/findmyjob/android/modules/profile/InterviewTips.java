package com.findmyjob.android.modules.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.findmyjob.android.R;
import com.findmyjob.android.model.constants.Adapter;
import com.findmyjob.android.model.constants.Tips;

import java.util.ArrayList;
import java.util.List;

public class InterviewTips extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;
    List<Tips> tips;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Button finishBtn;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interview_tips_activity);
        context=this;
        tips = new ArrayList<>();
        tips.add(new Tips(R.drawable.lookthepart,"1. Look the part","Research by psychologists has shown that people decide what they think of you within the first four minutes of a first meting , so make sure that you look the part and have te perfect interview attire."));
        tips.add(new Tips(R.drawable.research,"2. Do Your Research","learn about company from sources such as info published  on social media and website, news articles and reviews on Glassdoor from from current and previous employees "));
        tips.add(new Tips(R.drawable.practice,"3. Practice","think about your skills and accomplishments that are relevant to the role and practice answering questions in a mock interview set up. Dont rehearse answers word for word, making a note of key point will suffice."));
        tips.add(new Tips(R.drawable.relax,"4. Relax","Remind yourself that you have been invited to interview because te company believe you may be a good fit for the job based on information you hve provided"));
        tips.add(new Tips(R.drawable.honesty,"5. Be honest!","The purpose of an interview is to meet you in person to determine whether your personality will be a fit for the company, so be honest with your answers."));
        adapter = new Adapter(tips, this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(30,0,30,0);
        colors = new Integer[]{
                getResources().getColor(R.color.tips_bg),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.tips_bg),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.tips_bg)
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position<(adapter.getCount()-1)&& position<(colors.length -1))
                {
                viewPager.setBackgroundColor(
                        (Integer) argbEvaluator.evaluate(
                                positionOffset,
                                colors[position],colors[position+1]
                        )
                    );
                }else {
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}