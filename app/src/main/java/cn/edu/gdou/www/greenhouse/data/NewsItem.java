package cn.edu.gdou.www.greenhouse.data;

import java.util.List;

/**
 * Created by Veyron on 2017/10/15.
 * Function：
 */

public class NewsItem {
    private String publishDateStr;
    private String title;
    private String url;
    private String content;
    private List<String> imageUrls;


    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setPublishDateStr(String publishDateStr) {
        this.publishDateStr = publishDateStr;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "publishDateStr='" + publishDateStr + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", imageUrls=" + imageUrls +
                '}';
    }

}
