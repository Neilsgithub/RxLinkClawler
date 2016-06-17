package io.afeozzz.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * Created by afeozzz on 17/06/16.
 */
public class LinkPreviewView extends FrameLayout {

    private TextView titleTextView;
    private ImageView imageView;
    private TextView descriptionTextView;

    public LinkPreviewView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.link_preview, null);
        titleTextView = (TextView) view.findViewById(R.id.title);
        descriptionTextView = (TextView) view.findViewById(R.id.description);
        imageView = (ImageView) view.findViewById(R.id.image);
        addView(view);
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setImage(String image) {
        Glide.with(getContext()).load(image).into(imageView);
    }

    public void setDescription(String description) {
        descriptionTextView.setText(description);
    }
}
