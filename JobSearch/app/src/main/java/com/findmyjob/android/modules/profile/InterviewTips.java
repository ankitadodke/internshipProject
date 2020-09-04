package com.findmyjob.android.modules.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interview_tips_activity);
        context=this;
        tips = new ArrayList<>();
        tips.add(new Tips(R.drawable.lookthepart,getString(R.string.int_tips_title_1),getString(R.string.desc_1)));
        tips.add(new Tips(R.drawable.research,getString(R.string.interview_tips_title_2),getString(R.string.desc_2)));
        tips.add(new Tips(R.drawable.practice,getString(R.string.int_tips_title3),getString(R.string.desc_3)));
        tips.add(new Tips(R.drawable.relax,getString(R.string.int_tips_title_),getString(R.string.desc_4)));
        tips.add(new Tips(R.drawable.honesty,getString(R.string.int_tips_title_4),getString(R.string.desc_5)));
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

        Button finishBtn = findViewById(R.id.finish);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}