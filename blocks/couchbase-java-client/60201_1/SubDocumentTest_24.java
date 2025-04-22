        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .addUnique("array", true, false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathExistsException.class).content(0);
