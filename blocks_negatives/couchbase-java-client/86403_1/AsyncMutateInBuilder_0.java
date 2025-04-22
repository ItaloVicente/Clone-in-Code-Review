        if (StringUtil.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path must not be empty for counter");
        }
        if (delta == 0L) {
            throw new BadDeltaException("Delta must not be 0");
        }
        this.mutationSpecs.add(new MutationSpec(Mutation.COUNTER, path, delta, new SubdocOptionsBuilder().createPath(createPath)));
        return this;
