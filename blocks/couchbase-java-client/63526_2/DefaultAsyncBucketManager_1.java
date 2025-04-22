        return createN1qlIndex(indexName,  Arrays.asList(fields), null, ignoreIfExist, defer);
    }

    @Override
    public Observable<Boolean> createN1qlIndex(final String indexName, List<Object> fields, Expression whereClause,
            final boolean ignoreIfExist, boolean defer) {
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("At least one field is required for secondary index");
        }

        int i = -1;
        Expression firstExpression = expressionOrIdentifier(fields.get(0));
        Expression[] otherExpressions = new Expression[fields.size() - 1];
        for (Object field : fields) {
            if (i > -1) {
                otherExpressions[i] = expressionOrIdentifier(field);
            } //otherwise skip first expression, already processed
            i++;
