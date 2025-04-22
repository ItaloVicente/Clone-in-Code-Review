    public ParametrizedQuery(Statement statement, JsonObject namedParams) {
        this.statement = statement;
        this.params = namedParams;
    }

    @Override
    public Statement statement() {
        return this.statement;
