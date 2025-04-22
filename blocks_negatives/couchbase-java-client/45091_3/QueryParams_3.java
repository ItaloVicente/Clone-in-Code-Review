        this.scanVector = JsonArray.from(fullVector);
        return this;
    }

    /**
     * Sets scan consistency to AT_PLUS, using a sparse scan vector.
     *
     * This implements bounded consistency. The request includes a scan_vector parameter and value,
     * which is used as a lower bound. This can be used to implement read-your-own-writes (RYOW).
     *
     * @param sparseScanVector A {@link Map} giving the sequence number/timestamp for each vBucket number (String)
     * @return this {@link QueryParams} for chaining.
     */
    public QueryParams consistencyAtPlus(Map<String, Integer> sparseScanVector) {
        this.consistency = ScanConsistency.AT_PLUS;
        this.scanVector = JsonObject.from(sparseScanVector);
        return this;
    }

    /**
     * Sets scan consistency to NOT_BOUNDED and unsets the {@link #scanWait} if it was set.
     *
     * This is the default (for single-statement requests).
     * No timestamp vector is used in the index scan.
     * This is also the fastest mode, because we avoid the cost of obtaining the vector,
     * and we also avoid any wait for the index to catch up to the vector.
     *
     * @return this {@link QueryParams} for chaining.
     */
    public QueryParams consistencyNotBounded() {
        this.consistency = ScanConsistency.NOT_BOUNDED;
        this.scanVector = null;
        this.scanWait = null;
        return this;
    }

    /**
     * Sets scan consistency to REQUEST_PLUS.
     *
     * This implements strong consistency per request.
     * Before processing the request, a current vector is obtained.
     * The vector is used as a lower bound for the statements in the request.
     * If there are DML statements in the request, RYOW is also applied within the request.
     *
     * @return this {@link QueryParams} for chaining.
     */
    public QueryParams consistencyRequestPlus() {
        this.consistency = ScanConsistency.REQUEST_PLUS;
        this.scanVector = null;
        return this;
    }

    /**
     * Sets scan consistency to STATEMENT_PLUS.
     *
     * This implements strong consistency per statement.
     * Before processing each statement, a current vector is obtained
     * and used as a lower bound for that statement.
     *
     * @return this {@link QueryParams} for chaining.
     */
    public QueryParams consistencyStatementPlus() {
        this.consistency = ScanConsistency.STATEMENT_PLUS;
        this.scanVector = null;
