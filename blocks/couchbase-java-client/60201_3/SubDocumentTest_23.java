        long delta = 1000L;
        final String path = "counters.a";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .counter(path, delta, true)
                .doMutate();
