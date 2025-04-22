        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .addUnique("anotherArray", "arrayInsert", false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathNotFoundException.class).content(0);
