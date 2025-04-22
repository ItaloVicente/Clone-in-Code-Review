        DocumentFragment<Mutation> singleResult = ctx.bucket()
                .mutateIn(key)
                .replace("boolean.some", "string")
                .doMutate();

        singleResult.content(0);
