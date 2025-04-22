    /**
     * Create a new {@link Query} with a plain un-parametrized {@link Statement}.
     * @param statement the {@link Statement} to execute
     */
    public SimpleQuery(Statement statement) {
        super(statement, null);
    }

    public SimpleQuery(Statement statement, QueryParams params) {
