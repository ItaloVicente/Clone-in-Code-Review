    @Override
    public FromPath selectDistinctRaw(Expression expression) {
        element(new SelectElement(SelectType.DISTINCT_RAW, expression));
        return new DefaultFromPath(this);
    }

