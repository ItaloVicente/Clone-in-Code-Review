        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .addUnique("array", JsonObject.create().put("object", true), false)
                .doMutate();

        assertNotNull(result);
        verifyException(result, CannotInsertValueException.class).content(0);
