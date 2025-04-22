        this.mutationSpecs.add(new MutationSpec(Mutation.REPLACE, path, fragment));
        return this;
    }

    public <T> AsyncMutateInBuilder replace(String path, T fragment, SubdocOptionsBuilder optionsBuilder) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for replace");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.REPLACE, path, fragment, optionsBuilder));
