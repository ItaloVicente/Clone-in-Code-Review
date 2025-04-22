        asyncBuilder.upsert(path, fragment, new SubdocOptionsBuilder().createParents(createParents));
        return this;
    }

    public <T> MutateInBuilder upsert(String path, T fragment) {
        asyncBuilder.upsert(path, fragment);
