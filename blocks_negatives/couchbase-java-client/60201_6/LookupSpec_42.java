
    /**
     * Create a GET lookup specification.
     */
    public static LookupSpec get(String path) {
        return new LookupSpec(Lookup.GET, path);
    }

    /**
     * Create an EXIST lookup specification.
     */
    public static LookupSpec exists(String path) {
        return new LookupSpec(Lookup.EXIST, path);
    }
