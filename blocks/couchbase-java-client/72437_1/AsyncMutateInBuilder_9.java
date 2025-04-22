        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, new MultiValue<T>(values), new SubdocOptionsBuilder().createParents(createParents)));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayPrependAll(String path, Collection<T> values, SubdocOptionsBuilder optionsBuilder) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, new MultiValue<T>(values), optionsBuilder));
