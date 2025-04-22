        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .counter("sub.value", 1000L, false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathMismatchException.class).content(0);
