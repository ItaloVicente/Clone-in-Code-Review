    /**
     * Experimental: Queries a N1QL secondary index with the default query timeout.
     *
     * This method throws under the following conditions:
     *
     * - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while "in flight" on the wire: {@link RequestCancelledException}
     *
     * @param query the full N1QL string, including statement and any other additional parameter.
     * @return a result containing all found rows and additional information.
     */
    QueryResult queryRaw(String query);

    /**
     * Experimental: Queries a N1QL secondary index with a custom timeout.
     *
     * This method throws under the following conditions:
     *
     * - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while "in flight" on the wire: {@link RequestCancelledException}
     *
     * @param query the full N1QL string including statement and any other additional parameter.
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @return a result containing all found rows and additional information.
     */
    QueryResult queryRaw(String query, long timeout, TimeUnit timeUnit);

