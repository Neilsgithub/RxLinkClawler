package io.afeozzz.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.File;

import io.afeozzz.rxlinkcrawler.LinkPreview;
import io.afeozzz.rxlinkcrawler.RxLinkClawler;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button grabButton;
    private OkHttpClient okHttpClient;
    private LinearLayout linearLayout;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.text_field);
        grabButton = (Button) findViewById(R.id.find);
        linearLayout = (LinearLayout) findViewById(R.id.content);

        long cacheByteCount = 1024L * 1024L * 100L;
        Cache cache = new Cache(new File("cache"), cacheByteCount);
        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        grabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabLink();
            }
        });
    }

    private void grabLink() {
        Subscription subscription = RxLinkClawler.grab(okHttpClient, editText.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LinkPreview>() {
                    @Override
                    public void call(LinkPreview clawlerPreview) {
                        if (clawlerPreview != null) {
                            LinkPreviewView linkPreviewView = new LinkPreviewView(MainActivity.this);
                            linkPreviewView.setDescription(clawlerPreview.getDescription());
                            linkPreviewView.setTitle(clawlerPreview.getTitle());
                            linkPreviewView.setImage(clawlerPreview.image());
                            linearLayout.addView(linkPreviewView);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        compositeSubscription.add(subscription);
    }

    @Override
    protected void onPause() {
        compositeSubscription.clear();
        super.onPause();
    }
}
