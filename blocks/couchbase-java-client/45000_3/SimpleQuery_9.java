    protected String statementType() {
        return "statement";
    }

    @Override
    protected Object statementValue() {
        return this.statement.toString();
    }

    @Override
    protected JsonValue statementParameters() {
        return null;
