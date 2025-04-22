     * Experimental, Internal: Execute a prepared query, without retry behavior.
     *
     * The returned {@link Observable} can error under the following conditions:
     *
     * - The prepared statement's name was not known on the server and it was re-prepared:
     * {@link NamedPreparedStatementException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while "in flight" on the wire: {@link RequestCancelledException}
     *
     * @param query the prepared query (including parameters).
     * @return a result similar to {@link #executeRaw(String)} if the prepared query could be executed,
     * or similar to {@link #prepare(Statement)} )} if it had to be re-prepared on the node.
