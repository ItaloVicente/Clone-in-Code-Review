
    public <T> MutateInBuilder remove(String path, SubdocOptionsBuilder optionsBuilder) {
        if (optionsBuilder.createParents()) {
            throw new IllegalArgumentException("Options createParents are not supported for remove");
        }
        asyncBuilder.remove(path, optionsBuilder);
        return this;
    }


