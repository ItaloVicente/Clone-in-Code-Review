     * - The prepared statement's name was not known on the server and it was re-prepared:
     * {@link NamedPreparedStatementException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while "in flight" on the wire: {@link RequestCancelledException}
     *
     * @param query the prepared query (including parameters).
     * @return a result similar to {@link #queryRaw(String)} if the prepared query could be executed,
     * or similar to {@link #prepare(String)} if it had to be re-prepared on the node.
