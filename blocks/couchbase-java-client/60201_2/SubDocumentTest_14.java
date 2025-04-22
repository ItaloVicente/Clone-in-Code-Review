        DocumentFragment<Mutation> singleResult = ctx.bucket()
                .mutateIn(key)
                .replace("sub.value", true)
                .doMutate();

        assertNotNull(singleResult);
        assertEquals(ResponseStatus.SUCCESS, singleResult.status(0));
        assertNotEquals(0, singleResult.cas());
