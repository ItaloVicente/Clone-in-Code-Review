        DocumentFragment<Mutation> singleResult = ctx.bucket()
                .mutateIn(key)
                .replace( "array.some", "string")
                .doMutate();

        singleResult.content(0);
