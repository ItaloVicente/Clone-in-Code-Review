    @Test
    public void bucketsWithCustomTranscodersAreCached() throws Exception {
        assertSame(openBucket(customTranscoders), openBucket(customTranscoders));
    }

    @Test
    public void bucketsWithoutCustomTranscodersAreCached() throws Exception {
        assertSame(openBucket(), openBucket());
    }

    @Test
    public void differenceInTranscodersCausesBucketCacheMiss() throws Exception {
        assertNotEquals(openBucket(), openBucket(customTranscoders));
    }

