package io.afeozzz.rxlinkcrawler;

/**
 * Created by afeozzz on 16/06/16.
 */
public class LinkPreview {

    private final String url;

    private final String images;

    private final String title;

    private final String description;

    public LinkPreview(String url, String images, String title, String description) {
        this.url = url;
        this.images = images;
        this.title = title;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String image() {
        return images;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
