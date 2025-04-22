        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .insert("array.some", "string", false)
                .doMutate();

        result.content(0);
