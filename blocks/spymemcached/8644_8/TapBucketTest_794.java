    MembaseClient mc =
        new MembaseClient(
            Arrays.asList(new URI("http://localhost:8091/pools")), "bucket",
            "bucket", "password");
    TapClient tc =
        new TapClient(Arrays.asList(new URI("http://localhost:8091/pools")),
            "bucket", "bucket", "password");
    tc.tapBackfill(null, 5, TimeUnit.SECONDS);
