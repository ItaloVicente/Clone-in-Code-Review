        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_INSERT, path, value));
        return this;
    }

    public <T> AsyncMutateInBuilder arrayInsert(String path, T value, SubdocOptionsBuilder optionsBuilder) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for arrayInsert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.ARRAY_INSERT, path, value, optionsBuilder));
