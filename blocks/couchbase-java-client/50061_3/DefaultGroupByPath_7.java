    @Override
    public LettingPath groupBy(String... identifiers) {
        Expression[] expressions = new Expression[identifiers.length];
        for (int i = 0; i < identifiers.length; i++) {
            expressions[i] = x(identifiers[i]);
        }
        return groupBy(expressions);
    }
