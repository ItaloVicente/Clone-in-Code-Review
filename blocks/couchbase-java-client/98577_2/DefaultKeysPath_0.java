
    @Override
    public LetPath on(final Expression expression) {
        element(new Element() {
            @Override
            public String export() {
                return "ON " + expression.toString();
            }
        });
        return new DefaultLetPath(this);
    }
