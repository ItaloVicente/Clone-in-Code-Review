    IWorkbenchPart getPart(boolean restore);

    /**
     * @see IWorkbenchPart#getTitle
     */
    String getTitle();

    /**
     * @see IWorkbenchPart#getTitleImage
     */
    Image getTitleImage();

    /**
     * @see IWorkbenchPart#getTitleToolTip
     */
    String getTitleToolTip();

    /**
     * @see IWorkbenchPartSite#getId
     */
    String getId();

    /**
     * @see IWorkbenchPart#addPropertyListener
     */
    void addPropertyListener(IPropertyListener listener);

    /**
     * @see IWorkbenchPart#removePropertyListener
     */
    void removePropertyListener(IPropertyListener listener);

    /**
     * Returns the workbench page that contains this part
     */
    IWorkbenchPage getPage();

    /**
     * Returns the name of the part, as it should be shown in tabs.
     *
     * @return the part name
     *
     * @since 3.0
     */
    String getPartName();

    /**
     * Returns the content description for the part (or the empty string if none)
     *
     * @return the content description for the part
     *
     * @since 3.0
     */
    String getContentDescription();


    /**
     * Returns whether the part is dirty (i.e. has unsaved changes).
     *
     * @return <code>true</code> if the part is dirty, <code>false</code> otherwise
     *
     * @since 3.2 (previously existed on IEditorReference)
     */
    boolean isDirty();

    /**
