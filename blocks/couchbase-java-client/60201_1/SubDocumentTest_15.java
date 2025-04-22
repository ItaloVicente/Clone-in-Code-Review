        final String path = "sub";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .extend(path, "string", ExtendDirection.BACK, false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathMismatchException.class).content(path);
        verifyException(result, PathMismatchException.class).content(0);
