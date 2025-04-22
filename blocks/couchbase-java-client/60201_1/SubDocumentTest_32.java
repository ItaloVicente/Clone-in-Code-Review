        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .counter("counters.a", 1000L, false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathNotFoundException.class).content(0);
