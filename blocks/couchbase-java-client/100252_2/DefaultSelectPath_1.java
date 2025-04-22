    @Override
    public FromPath selectDistinctRaw(Expression expression) {
        element(new SelectElement(SelectType.DISTINCTRAW, expression));
        return new DefaultFromPath(this);
    }

