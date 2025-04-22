
    @Override
    protected Object statementValue() {
        return statement().plan();
    }

    @Override
    public QueryPlan statement() {
        return (QueryPlan) super.statement();
    }
