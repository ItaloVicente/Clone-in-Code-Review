        DocumentFragment<Mutation> singleResult = ctx.bucket()
                .mutateIn(key)
                .upsert("array.some", "string", false)
                .doMutate();

        singleResult.content(0);
