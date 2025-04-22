    public <T> AsyncMutateInBuilder arrayAppendAll(String path, Collection<T> values, boolean createParents) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_LAST, path, new MultiValue<T>(values), createParents));
                return this;
    }

    public <T> AsyncMutateInBuilder arrayAppendAll(String path, T... values) {
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_PUSH_LAST, path, new MultiValue<T>(values), false));
        return this;
    }

