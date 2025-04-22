        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, value, new SubdocOptionsBuilder().createParents(createParents)));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayPrepend(String path, T value) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, value));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayPrepend(String path, T value, SubdocOptionsBuilder optionsBuilder) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, value, optionsBuilder));
