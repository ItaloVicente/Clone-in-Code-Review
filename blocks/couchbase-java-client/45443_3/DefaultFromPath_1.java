
    @Override
    public AsPath from(Expression from) {
        element(new FromElement(from.toString()));
        return new DefaultAsPath(this);
    }
