
        final String path = "array";
        final String value = "newElement";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .extend(path, value, ExtendDirection.BACK, false)
                .doMutate();
