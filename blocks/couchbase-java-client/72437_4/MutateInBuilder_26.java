        asyncBuilder.arrayAddUnique(path, value, new SubdocOptionsBuilder().createParents(createParents));
        return this;
    }

    public <T> MutateInBuilder arrayAddUnique(String path, T value) {
        asyncBuilder.arrayAddUnique(path, value);
        return this;
    }

    public <T> MutateInBuilder arrayAddUnique(String path, T value, SubdocOptionsBuilder optionsBuilder) {
        asyncBuilder.arrayAddUnique(path, value, optionsBuilder);
