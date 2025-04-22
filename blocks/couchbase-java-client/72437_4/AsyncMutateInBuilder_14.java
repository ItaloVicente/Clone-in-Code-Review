        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_ADD_UNIQUE, path, value, new SubdocOptionsBuilder().createParents(createParents)));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayAddUnique(String path, T value) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_ADD_UNIQUE, path, value));
        return this;
    }


    public <T> AsyncMutateInBuilder arrayAddUnique(String path, T value, SubdocOptionsBuilder optionsBuilder) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_ADD_UNIQUE, path, value, optionsBuilder));
