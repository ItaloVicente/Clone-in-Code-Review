package com.couchbase.client.java.fts.result.impl;

import com.couchbase.client.java.fts.result.SearchStatus;

public class DefaultSearchStatus implements SearchStatus {

    private final long totalCount;
    private final long errorCount;
    private final long rowCount;

    public DefaultSearchStatus(long totalCount, long errorCount, long rowCount) {
        this.totalCount = totalCount;
        this.errorCount = errorCount;
        this.rowCount = rowCount;
    }

    @Override
    public long totalCount() {
        return this.totalCount;
    }

    @Override
    public long rowCount() {
        return this.rowCount;
    }

    @Override
    public long errorCount() {
        return this.errorCount;
    }

    @Override
    public boolean isSuccess() {
        return errorCount() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DefaultSearchStatus that = (DefaultSearchStatus) o;

        if (totalCount != that.totalCount) {
            return false;
        }
        if (errorCount != that.errorCount) {
            return false;
        }
        return rowCount == that.rowCount;

    }

    @Override
    public int hashCode() {
        int result = (int) (totalCount ^ (totalCount >>> 32));
        result = 31 * result + (int) (errorCount ^ (errorCount >>> 32));
        result = 31 * result + (int) (rowCount ^ (rowCount >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "DefaultSearchStatus{" +
                "totalCount=" + totalCount +
                ", errorCount=" + errorCount +
                ", rowCount=" + rowCount +
                '}';
    }
}
