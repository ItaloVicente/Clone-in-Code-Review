        asyncBuilder.counter(path, delta, new SubdocOptionsBuilder().createParents(createParents));
        return this;
    }

    public MutateInBuilder counter(String path, long delta) {
        asyncBuilder.counter(path, delta);
        return this;
    }

    public MutateInBuilder counter(String path, long delta, SubdocOptionsBuilder optionsBuilder) {
        asyncBuilder.counter(path, delta, optionsBuilder);
