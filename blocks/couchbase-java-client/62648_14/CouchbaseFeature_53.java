package com.couchbase.client.java.fts.result.impl;

import com.couchbase.client.java.fts.result.SearchStatus;

public class DefaultSearchStatus implements SearchStatus {

    private final long totalCount;
    private final long errorCount;
    private final long successCount;

    public DefaultSearchStatus(long totalCount, long errorCount, long successCount) {
        this.totalCount = totalCount;
        this.errorCount = errorCount;
        this.successCount = successCount;
    }

    @Override
    public long totalCount() {
        return this.totalCount;
    }

    @Override
    public long successCount() {
        return this.successCount;
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
        return successCount == that.successCount;

    }

    @Override
    public int hashCode() {
        int result = (int) (totalCount ^ (totalCount >>> 32));
        result = 31 * result + (int) (errorCount ^ (errorCount >>> 32));
        result = 31 * result + (int) (successCount ^ (successCount >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "DefaultSearchStatus{" +
                "totalCount=" + totalCount +
                ", errorCount=" + errorCount +
                ", successCount=" + successCount +
                '}';
    }
}
