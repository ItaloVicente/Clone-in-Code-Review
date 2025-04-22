        this.mutationSpecs.add(new MutationSpec(Mutation.COUNTER, path, delta, new SubdocOptionsBuilder().createParents(createParents)));
        return this;
    }

    public AsyncMutateInBuilder counter(String path, long delta) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for counter");
        }
        if (delta == 0L) {
            throw new BadDeltaException("Delta must not be 0");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.COUNTER, path, delta));
        return this;
    }

    public AsyncMutateInBuilder counter(String path, long delta, SubdocOptionsBuilder optionsBuilder) {
        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for counter");
        }
        if (delta == 0L) {
            throw new BadDeltaException("Delta must not be 0");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.COUNTER, path, delta, optionsBuilder));
