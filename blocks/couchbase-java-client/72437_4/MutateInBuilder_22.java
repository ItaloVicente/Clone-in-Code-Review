        asyncBuilder.arrayPrependAll(path, values, new SubdocOptionsBuilder().createParents(createParents));
        return this;
    }


    public <T> MutateInBuilder arrayPrependAll(String path, Collection<T> values, SubdocOptionsBuilder optionsBuilder) {
        asyncBuilder.arrayPrependAll(path, values, optionsBuilder);
