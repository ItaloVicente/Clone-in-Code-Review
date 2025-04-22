        final String path = "array[-1]";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayInsert(path, "arrayInsert")
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathInvalidException.class).content(path);
        verifyException(result, PathInvalidException.class).content(0);
