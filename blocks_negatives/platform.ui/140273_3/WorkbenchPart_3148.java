        return contentDescription;
    }

    /**
     * Sets the content description for this part. The content description is typically
     * a short string describing the current contents of the part. Setting this to the
     * empty string will cause a default content description to be used. Clients should
     * call this method instead of overriding getContentDescription(). For views, the
     * content description is shown (by default) in a line near the top of the view. For
     * editors, the content description is shown beside the part name when showing a
     * list of editors. If the editor is open on a file, this typically contains the path
     * to the input file, without the filename or trailing slash.
     *
     * <p>
     * This may overwrite a value that was previously set in setTitle
     * </p>
     *
     * @param description the content description
     *
     * @since 3.0
     */
    protected void setContentDescription(String description) {
        internalSetContentDescription(description);

        setDefaultTitle();
    }

    void internalSetContentDescription(String description) {
        Assert.isNotNull(description);

        if (Util.equals(contentDescription, description)) {
