
    public <T> AsyncMutateInBuilder arrayAppendAll(String path, Collection<T> values, SubdocOptionsBuilder optionsBuilder) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_LAST, path, new MultiValue<T>(values), optionsBuilder));
        return this;
    }

