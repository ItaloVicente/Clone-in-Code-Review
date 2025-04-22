        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .addUnique("array", "arrayInsert", false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathMismatchException.class).content(0);
