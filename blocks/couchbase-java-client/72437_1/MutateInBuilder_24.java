        asyncBuilder.arrayAppendAll(path, values,  new SubdocOptionsBuilder().createParents(createParents));
        return this;
    }

    public <T> MutateInBuilder arrayAppendAll(String path, Collection<T> values, SubdocOptionsBuilder optionsBuilder) {
        asyncBuilder.arrayAppendAll(path, values, optionsBuilder);
