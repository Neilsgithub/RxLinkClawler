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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        LinkPreview linkPreview = (LinkPreview) obj;
        return this.url.equals(linkPreview.getUrl());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + url.hashCode();
        result = prime * result + images.hashCode();
        result = prime * result + title.hashCode();
        result = prime * result + description.hashCode();
        return result;
    }
}
