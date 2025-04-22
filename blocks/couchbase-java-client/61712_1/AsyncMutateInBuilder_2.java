    public <T> AsyncMutateInBuilder arrayInsertAll(String path, T... values) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for arrayInsert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_INSERT, path, new MultiValue<T>(values), false));
        return this;
    }

