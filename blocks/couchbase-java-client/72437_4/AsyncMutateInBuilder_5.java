        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_UPSERT, path, fragment, new SubdocOptionsBuilder().createParents(createParents)));
        return this;
    }

    public <T> AsyncMutateInBuilder upsert(String path, T fragment) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for upsert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_UPSERT, path, fragment));
        return this;
    }

    public <T> AsyncMutateInBuilder upsert(String path, T fragment, SubdocOptionsBuilder optionsBuilder) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for upsert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_UPSERT, path, fragment, optionsBuilder));
