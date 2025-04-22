    protected AbstractQuery(Statement statement, QueryParams params) {
        this.statement = statement;
        this.queryParameters = params;
    }

    @Override
    public QueryParams params() {
        return this.queryParameters;
    }

    @Override
    public Statement statement() {
        return this.statement;
    }

