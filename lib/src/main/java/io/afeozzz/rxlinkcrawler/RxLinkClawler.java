package io.afeozzz.rxlinkcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Func0;

/**
 * Created by afeozzz on 16/06/16.
 */
public class RxLinkClawler {

    public static Observable<LinkPreview> grab(final OkHttpClient okHttpClient, final String url) {
        return Observable.defer(new Func0<Observable<LinkPreview>>() {
            @Override
            public Observable<LinkPreview> call() {
                try {
                    return fetch(okHttpClient, url);
                } catch (IOException e) {
                    e.printStackTrace();
                    return Observable.error(e);
                }
            }
        });
    }

    private static Observable<LinkPreview> fetch(OkHttpClient okHttpClient, String url) throws IOException {

        if (url == null) {
            return Observable.error(new LinkPreviewException("Empty url"));
        }

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        int responseCode = response.code();

        String contentType = response.header("Content-Type");
        if (responseCode != 200 || contentType == null) {
            response.body().close();
            return Observable.error(new LinkPreviewException(response.body().string()));
        }

        Document document = Jsoup.parse(response.body().string(), url);
        return Observable.just(LinkPreviewParser.parse(url, document));
    }

}
