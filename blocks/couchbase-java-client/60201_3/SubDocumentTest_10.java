        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .insert("sub.some.path", 1024, false)
                .doMutate();

        result.content(0);
