        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_ADD, path, fragment, new SubdocOptionsBuilder().createParents(createParents)));
        return this;
    }

    public <T> AsyncMutateInBuilder insert(String path, T fragment) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for insert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_ADD, path, fragment));
        return this;
    }

    public <T> AsyncMutateInBuilder insert(String path, T fragment, SubdocOptionsBuilder optionsBuilder) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for insert");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.DICT_ADD, path, fragment, optionsBuilder));
