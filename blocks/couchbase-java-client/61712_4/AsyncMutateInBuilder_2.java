    public <T> AsyncMutateInBuilder arrayPrependAll(String path, Collection<T> values, boolean createParents) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, new MultiValue<T>(values), createParents));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayPrependAll(String path, T... values) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_FIRST, path, new MultiValue<T>(values), false));
        return this;
    }

