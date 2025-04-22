    /**
     * Experimental: Queries a N1QL secondary index and prepare an execution plan via the given
     * {@link Statement}. Statement can contain placeholders.
     * The resulting {@link PreparedPayload} can be cached and (re)used later in a {@link PreparedQuery}.
     *
     * The returned {@link Observable} can error under the following conditions:
     *
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while on the wire or the retry strategy cancelled it instead of
     *   retrying: {@link RequestCancelledException}
     *
     * @param statement the statement to prepare a plan for.
     * @return a {@link PreparedPayload} that can be cached and reused later in {@link PreparedQuery}.
     */

    /**
     * Experimental: Queries a N1QL secondary index and prepare an execution plan via the given
     * statement in {@link String} form. Statement can contain placeholders.
     * The resulting {@link PreparedPayload} can be cached and (re)used later in a {@link PreparedQuery}.
     *
     * The returned {@link Observable} can error under the following conditions:
     *
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while on the wire or the retry strategy cancelled it instead of
     *   retrying: {@link RequestCancelledException}
     *
     * @param statement the statement to prepare a plan for.
     * @return a {@link PreparedPayload} that can be cached and reused later in {@link PreparedQuery}.
     */

