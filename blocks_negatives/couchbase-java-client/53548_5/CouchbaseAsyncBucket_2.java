     * Experimental, Internal: Execute a prepared query.
     *
     * The returned {@link Observable} can error under the following conditions:
     *
     * - The prepared statement's name was not known on the server and it was re-prepared:
     * {@link NamedPreparedStatementException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while "in flight" on the wire: {@link RequestCancelledException}
