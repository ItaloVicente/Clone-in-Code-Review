        DocumentFragment<Mutation> singleResult = ctx.bucket()
                .mutateIn(key)
                .replace("sub.newValue", "sValue")
                .doMutate();

        singleResult.content(0);
