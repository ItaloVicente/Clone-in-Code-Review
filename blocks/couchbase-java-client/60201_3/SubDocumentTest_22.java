        final String path = "sub.counter";
        final long delta = 1000L;
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .counter(path, delta, false)
                .doMutate();

        assertThat(result.content(path), instanceOf(Long.class));
        assertEquals(1000L, result.content(path, Long.class).longValue());
        assertEquals(1000L, result.content(0, Long.class).longValue());
