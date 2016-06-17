package io.afeozzz.rxlinkcrawler;

/**
 * Created by afeozzz on 16/06/16.
 */
public class LinkPreviewException extends Exception {
    public LinkPreviewException () {

    }

    public LinkPreviewException (String message) {
        super (message);
    }

    public LinkPreviewException (Throwable cause) {
        super (cause);
    }

    public LinkPreviewException (String message, Throwable cause) {
        super (message, cause);
    }

}
