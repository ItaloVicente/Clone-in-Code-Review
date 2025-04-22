        String path = "int";
        long delta = -123L;
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .counter(path, delta, false)
                .doMutate();

        assertThat(result.content(path), instanceOf(Long.class));
        assertEquals(0L, result.content(path, Long.class).longValue());
