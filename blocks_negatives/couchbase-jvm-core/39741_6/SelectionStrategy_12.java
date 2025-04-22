     * If Compression is supported.
     */
    private final boolean compression;

    public SupportedDatatypes(final boolean json, final boolean compression) {
        this.json = json;
        this.compression = compression;
    }

    /**
     * If JSON Is supported.
