        verifyException(ctx.bucket()
                .mutateIn(key)
                .counter(path, delta, false))
                .doMutate();
        assertThat("second counter increment should have made the counter value too big",
                caughtException(), instanceOf(BadDeltaException.class));
