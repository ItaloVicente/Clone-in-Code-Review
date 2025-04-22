package com.couchbase.client.java.search.sort;

public class SearchSortScore extends SearchSort {

    @Override
    protected String identifier() {
        return "score";
    }

    @Override
    public SearchSortScore descending(boolean descending) {
        super.descending(descending);
        return this;
    }

}
