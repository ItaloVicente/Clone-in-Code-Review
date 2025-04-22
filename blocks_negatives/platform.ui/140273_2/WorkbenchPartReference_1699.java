    /**
     * Computes a new title for the part. Subclasses may override to change the default behavior.
     *
     * @return the title for the part
     */
    protected String computeTitle() {
        return getRawTitle();
    }

    /**
     * Returns the unmodified title for the part, or the empty string if none
     *
     * @return the unmodified title, as set by the IWorkbenchPart. Returns the empty string if none.
     */
    protected final String getRawTitle() {
