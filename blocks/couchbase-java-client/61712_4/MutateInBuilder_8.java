    public <T> MutateInBuilder arrayInsertAll(String path, Collection<T> values) {
        asyncBuilder.arrayInsertAll(path, values);
        return this;
    }

    public <T> MutateInBuilder arrayInsertAll(String path, T... values) {
        asyncBuilder.arrayInsertAll(path, values);
        return this;
    }

