    }

    /**
     * Sets whether this page is complete.
     * <p>
     * This information is typically used by the wizard to decide
     * when it is okay to move on to the next page or finish up.
     * </p>
     *
     * @param complete <code>true</code> if this page is complete, and
     *   and <code>false</code> otherwise
     * @see #isPageComplete()
     */
    public void setPageComplete(boolean complete) {
        isPageComplete = complete;
        if (isCurrentPage()) {
