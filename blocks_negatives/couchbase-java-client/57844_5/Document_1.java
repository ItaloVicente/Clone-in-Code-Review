    /**
     * The optional, opaque mutation token set after a successful mutation and if enabled on
     * the environment.
     *
     * Note that the mutation token is always null, unless they are explicitly enabled on the
     * environment, the server version is supported (>= 4.0.0) and the mutation operation succeeded.
     *
     * If set, it can be used for enhanced durability requirements, as well as optimized consistency
     * for N1QL queries.
     *
     * @return the mutation token if set, otherwise null.
     */
    MutationToken mutationToken();

