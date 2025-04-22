    public <T> MutateInBuilder insert(String path, T fragment) {
        asyncBuilder.insert(path, fragment);
        return this;
    }

    public <T> MutateInBuilder insert(String path, T fragment, SubdocOptionsBuilder optionsBuilder) {
        asyncBuilder.insert(path, fragment, optionsBuilder);
        return this;
    }

