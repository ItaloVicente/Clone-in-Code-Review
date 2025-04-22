        DocumentFragment<Mutation> singleResult = ctx.bucket()
                .mutateIn(key)
                .upsert("boolean.some", "string", false)
                .doMutate();
        
        singleResult.content(0);
