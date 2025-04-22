        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .insert("sub.value", true, false)
                .doMutate();

        result.content(0);
