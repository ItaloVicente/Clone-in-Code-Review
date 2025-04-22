        this.mutationSpecs.add(new MutationSpec(Mutation.DELETE, path, null));
        return this;
    }

    public <T> AsyncMutateInBuilder remove(String path, SubdocOptionsBuilder optionsBuilder) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for remove");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DELETE, path, null, optionsBuilder));
