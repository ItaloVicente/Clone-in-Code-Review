        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .insert("boolean.some", "string", false)
                .doMutate();

        result.content(0);
