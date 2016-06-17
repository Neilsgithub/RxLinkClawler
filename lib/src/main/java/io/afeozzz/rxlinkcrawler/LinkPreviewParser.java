package io.afeozzz.rxlinkcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by afeozzz on 16/06/16.
 */
public class LinkPreviewParser {
    private static final String URL = "url";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "images";
    private static final String TITLE = "title";

    public static LinkPreview parse(String url, Document document) {

        if (isImage(url)) {
            return new LinkPreview(url, url, "", "");
        }

        return createLinkPreviewFromDocument(url, document);
    }

    private static LinkPreview createLinkPreviewFromDocument(String url, Document document) {
        String content = document.toString();
        List<String> matches = Regex.pregMatchAll(content,
                Regex.METATAG_PATTERN, 1);

        String title = null;
        String image = null;
        String description = null;

        for (String match : matches) {
            final String lowerCase = match.toLowerCase();
            if (lowerCase.contains("property=\"og:url\"")
                    || lowerCase.contains("property='og:url'")
                    || lowerCase.contains("name=\"url\"")
                    || lowerCase.contains("name='url'"))
                url = separeMetaTagsContent(match);
            else if (lowerCase.contains("property=\"og:title\"")
                    || lowerCase.contains("property='og:title'")
                    || lowerCase.contains("name=\"title\"")
                    || lowerCase.contains("name='title'"))
                title = separeMetaTagsContent(match);
            else if (lowerCase
                    .contains("property=\"og:description\"")
                    || lowerCase
                    .contains("property='og:description'")
                    || lowerCase.contains("name=\"description\"")
                    || lowerCase.contains("name='description'"))
                description = separeMetaTagsContent(match);
            else if (lowerCase.contains("property=\"og:image\"")
                    || lowerCase.contains("property='og:image'")
                    || lowerCase.contains("name=\"image\"")
                    || lowerCase.contains("name='image'"))
                image = separeMetaTagsContent(match);
        }

        if (image == null) {
            List<String> images = getImages(document);
            if (images != null && images.size() > 0) {
                image = images.get(0);
            }
        }

        return new LinkPreview(url, image, title, description);
    }

    /**
     * Gets content from metatag
     */
    private static String separeMetaTagsContent(String content) {
        String result = Regex.pregMatch(content, Regex.METATAG_CONTENT_PATTERN,
                1);
        return htmlDecode(result);
    }

    private static String htmlDecode(String content) {
        return Jsoup.parse(content).text();
    }

    private static boolean isImage(String url) {
        return url.matches(Regex.IMAGE_PATTERN);
    }

    private static List<String> getImages(Document document) {
        List<String> matches = new ArrayList<String>();

        Elements media = document.select("[src]");

        for (Element srcElement : media) {
            if (srcElement.tagName().equals("img")) {
                matches.add(srcElement.attr("abs:src"));
            }
        }

        return matches;
    }

}
