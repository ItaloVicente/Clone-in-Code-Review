        String path = "int";
        long delta = 1000L;
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .counter(path, delta, false)
                .doMutate();
