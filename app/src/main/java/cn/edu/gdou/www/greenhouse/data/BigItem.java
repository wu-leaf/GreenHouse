package cn.edu.gdou.www.greenhouse.data;

import java.util.List;

/**
 * Created by Veyron on 2017/10/15.
 * Function：
 */

public class BigItem {

    private boolean hasNext;
    private String dataType;
    private String pageToken;
    private List<NewsItem> data;



    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public List<NewsItem> getData() {
        return data;
    }

    public void setData(List<NewsItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BigItem{" +
                "hasNext=" + hasNext +
                ", dataType='" + dataType + '\'' +
                ", pageToken='" + pageToken + '\'' +
                ", data=" + data +
                '}';
    }

}
