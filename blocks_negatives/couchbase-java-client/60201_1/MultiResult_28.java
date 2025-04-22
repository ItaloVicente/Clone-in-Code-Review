    public static LookupResult createGetResult(String path, ResponseStatus status, Object value) {
        return new LookupResult(path, Lookup.GET, status, value);
    }

    /**
     * Create a {@link LookupResult} that corresponds to a EXIST.
     *
     * @param path the path looked up.
     * @param status the status of the EXIST, its {@link ResponseStatus#isSuccess() isSuccess}
     *               giving the LookupResult's value.
     * @return the EXIST LookupResult.
     */
    public static LookupResult createExistResult(String path, ResponseStatus status) {
        return new LookupResult(path, Lookup.EXIST, status, status.isSuccess());
