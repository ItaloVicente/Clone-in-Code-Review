        asyncBuilder.arrayAppend(path, value, new SubdocOptionsBuilder().createParents(createParents));
        return this;
    }

    public <T> MutateInBuilder arrayAppend(String path, T value) {
        asyncBuilder.arrayAppend(path, value);
        return this;
    }

    public <T> MutateInBuilder arrayAppend(String path, T value, SubdocOptionsBuilder optionsBuilder) {
        asyncBuilder.arrayAppend(path, value, optionsBuilder);
