    public <T> MutateInBuilder arrayAppendAll(String path, Collection<T> values, boolean createParents) {
        asyncBuilder.arrayAppendAll(path, values, createParents);
        return this;
    }

    public <T> MutateInBuilder arrayAppendAll(String path, T... values) {
        asyncBuilder.arrayAppendAll(path, values);
        return this;
    }

