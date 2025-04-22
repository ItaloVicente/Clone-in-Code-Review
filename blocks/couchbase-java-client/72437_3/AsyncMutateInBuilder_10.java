        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_LAST, path, value, new SubdocOptionsBuilder().createParents(createParents)));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayAppend(String path, T value) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_LAST, path, value));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayAppend(String path, T value, SubdocOptionsBuilder optionsBuilder) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_LAST, path, value, optionsBuilder));
