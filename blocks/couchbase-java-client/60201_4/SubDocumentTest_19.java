        final String path = "array";
        final String value = "newElement";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .pushFront(path, value, false)
                .doMutate();
