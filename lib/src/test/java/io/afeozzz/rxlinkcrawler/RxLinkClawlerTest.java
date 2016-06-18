package io.afeozzz.rxlinkcrawler;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import okhttp3.OkHttpClient;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by afeozzz on 18/06/16.
 */
public class RxLinkClawlerTest {
    private OkHttpClient okHttpClient;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialize() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Test
    public void emptyUrl() {
        String url = null;
        TestSubscriber<LinkPreview> testSubscriber = new TestSubscriber<>();
        Subscription subscription = RxLinkClawler.grab(okHttpClient, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testSubscriber);

        testSubscriber.assertError(LinkPreviewException.class);
        subscription.unsubscribe();
        testSubscriber.assertUnsubscribed();
    }

    @Test
    public void shouldLoadUrl() {


        String url = "https://www.youtube.com/watch?v=e-ORhEE9VVg";

        String description = "Watch Taylor's new video for Blank Space. No animals, trees, automobiles or actors were harmed in the making of this video. Taylor’s new release 1989 is Av...";
        String title = "Taylor Swift - Blank Space";
        String image = "https://i.ytimg.com/vi/e-ORhEE9VVg/maxresdefault.jpg";
        LinkPreview linkPreview = new LinkPreview(url, image, title, description);


        TestSubscriber<LinkPreview> testSubscriber = new TestSubscriber<>();
        Subscription subscription = RxLinkClawler.grab(okHttpClient, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(linkPreview);
        subscription.unsubscribe();
        testSubscriber.assertUnsubscribed();
    }
}
