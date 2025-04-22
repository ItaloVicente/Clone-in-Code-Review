        result = ctx.bucket()
                .mutateIn(key)
                .counter(path, delta, false)
                .doMutate();

        assertNotNull(result);
        assertEquals(ResponseStatus.SUBDOC_DELTA_RANGE, result.status(0));
        verifyException(result).content(path);
        assertThat("second counter increment should have made the counter value too big",
                caughtException(), instanceOf(DeltaTooBigException.class));
