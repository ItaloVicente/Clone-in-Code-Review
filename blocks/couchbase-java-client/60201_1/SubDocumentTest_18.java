        final String path = "sub.array";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .extend(path, "newElement", ExtendDirection.FRONT, false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathNotFoundException.class).content(path);
