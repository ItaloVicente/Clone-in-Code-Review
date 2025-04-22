        DocumentFragment<Mutation> singleResult = ctx.bucket()
                .mutateIn(key)
                .upsert("sub.some.path", 1024, false)
                .doMutate();

        singleResult.content(0);
