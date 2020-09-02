package com.findmyjob.android.model.constants;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.findmyjob.android.R;
import com.findmyjob.android.modules.profile.InterviewTips;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class Adapter extends PagerAdapter {

    public Adapter(List<Tips> tips,  Context context) {
        this.tips = tips;
        this.context = context;
    }

    private List<Tips> tips;
    private LayoutInflater layoutInflater;
    private Context context;


    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.tips,container,false);
        ImageView image;
        TextView title, subtitle;
        image= view.findViewById(R.id.image);
        title=view.findViewById(R.id.heading);
        subtitle=view.findViewById(R.id.subtitle);

        image.setImageResource(tips.get(position).getImage());
        title.setText(tips.get(position).getTitle());
        subtitle.setText(tips.get(position).getSubtitle());
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }
}
