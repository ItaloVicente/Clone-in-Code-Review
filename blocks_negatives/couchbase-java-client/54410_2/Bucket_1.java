
    /**
     * Experimental: Queries a N1QL secondary index and prepare an execution plan via the given
     * {@link String} statement, with the default timeout. Statement can contain placeholders.
     *
     * The resulting {@link PreparedPayload} can be cached and (re)used later in a {@link PreparedQuery}.
     *
     * This method throws under the following conditions:
     *
     * - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while on the wire or the retry strategy cancelled it instead of
     *   retrying: {@link RequestCancelledException}
     *
     * @param statement the statement to prepare a plan for.
     * @return a {@link PreparedPayload} that can be cached and reused later in {@link PreparedQuery}.
     */

    /**
     * Experimental: Queries a N1QL secondary index and prepare an execution plan via the given
     * {@link Statement}, with the default timeout. Statement can contain placeholders.
     *
     * The resulting {@link PreparedPayload} can be cached and (re)used later in a {@link PreparedQuery}.
     *
     * This method throws under the following conditions:
     *
     * - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while on the wire or the retry strategy cancelled it instead of
     *   retrying: {@link RequestCancelledException}
     *
     * @param statement the statement to prepare a plan for.
     * @return a {@link PreparedPayload} that can be cached and reused later in {@link PreparedQuery}.
     */

    /**
     * Experimental: Queries a N1QL secondary index and prepare an execution plan via the given
     * {@link String} statement, with a custom timeout. Statement can contain placeholders.
     *
     * The resulting {@link PreparedPayload} can be cached and (re)used later in a {@link PreparedQuery}.
     *
     * This method throws under the following conditions:
     *
     * - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while on the wire or the retry strategy cancelled it instead of
     *   retrying: {@link RequestCancelledException}
     *
     * @param statement the statement to prepare a plan for.
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @return a {@link PreparedPayload} that can be cached and reused later in {@link PreparedQuery}.
     */

    /**
     * Experimental: Queries a N1QL secondary index and prepare an execution plan via the given
     * {@link Statement}, with a custom timeout. Statement can contain placeholders.
     *
     * The resulting {@link PreparedPayload} can be cached and (re)used later in a {@link PreparedQuery}.
     *
     * This method throws under the following conditions:
     *
     * - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     * - The producer outpaces the SDK: {@link BackpressureException}
     * - The operation had to be cancelled while on the wire or the retry strategy cancelled it instead of
     *   retrying: {@link RequestCancelledException}
     *
     * @param statement the statement to prepare a plan for.
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @return a {@link PreparedPayload} that can be cached and reused later in {@link PreparedQuery}.
     */

