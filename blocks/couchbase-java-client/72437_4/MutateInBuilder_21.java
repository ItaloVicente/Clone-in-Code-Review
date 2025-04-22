        asyncBuilder.arrayPrepend(path, value, new SubdocOptionsBuilder().createParents(createParents));
        return this;
    }


    public <T> MutateInBuilder arrayPrepend(String path, T value) {
        asyncBuilder.arrayPrepend(path, value);
        return this;
    }

    public <T> MutateInBuilder arrayPrepend(String path, T value, SubdocOptionsBuilder optionsBuilder) {
        asyncBuilder.arrayPrepend(path, value, optionsBuilder);
