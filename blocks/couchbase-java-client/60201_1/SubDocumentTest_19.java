        final String path = "array[5]";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .arrayInsert(path, "arrayInsert")
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathNotFoundException.class).content(path);
        verifyException(result, PathNotFoundException.class).content(0);
