    /**
     * This only denotes initial success in parsing the query. As rows are processed, it could be
     * that a late failure occurs. See {@link #finalSuccess} for the end of processing status.
     *
     * {@inheritDoc}
     *
     * @return true if no errors were detected upfront / query was successfully parsed.
     */
