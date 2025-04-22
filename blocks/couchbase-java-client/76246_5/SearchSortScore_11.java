package com.couchbase.client.java.search.sort;

public class SearchSortId extends SearchSort {

    @Override
    protected String identifier() {
        return "id";
    }

    @Override
    public SearchSortId descending(boolean descending) {
        super.descending(descending);
        return this;
    }

}
