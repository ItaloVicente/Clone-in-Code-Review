        return 4;
    }

    /**
     * Returns the preferred preview height.
     *
     * @return the height, or <code>-1</code> if no previewer
     *  is installed
     */
    public int getPreferredPreviewHeight() {
        if (previewer == null) {
