package com.kobuta.rakuchin.hojinzei.vo;

import java.util.List;

public class PageVo<T> {

    private List<T> items;

    private long total;

    private int page;

    private int limit;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public PageVo (List items, long total, int page, int limit) {
        this.setTotal(total);
        this.setLimit(limit);
        this.setPage(page);
        this.setItems(items);
    }
}
