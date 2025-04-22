    /**
     * Returns the elements that are contained in this working set.
     * <p>
     * This method might throw an {@link IllegalStateException} if
     * the working set is invalid.
     * </p>
     * @return	the working set's elements
     */
    IAdaptable[] getElements();

    /**
     * Returns the working set id. Returns <code>null</code> if no
     * working set id has been set.
     * This is one of the ids defined by extensions of the
     * org.eclipse.ui.workingSets extension point.
     * It is used by the workbench to determine the page to use in
     * the working set edit wizard. The default resource edit page
     * is used if this value is <code>null</code>.
     *
     * @return the working set id. May be <code>null</code>
     * @since 2.1
     */
    String getId();

    /**
     * Returns the working set icon.
     * Currently, this is one of the icons specified in the extensions
     * of the org.eclipse.ui.workingSets extension point.
     * The extension is identified using the value returned by
     * <code>getId()</code>.
     * Returns <code>null</code> if no icon has been specified in the
     * extension or if <code>getId()</code> returns <code>null</code>.
     *
     * @return the working set icon or <code>null</code>.
     * @since 2.1
     * @deprecated use {@link #getImageDescriptor()} instead
     */
    @Deprecated ImageDescriptor getImage();

    /**
     * Returns the working set icon.
     * Currently, this is one of the icons specified in the extensions
     * of the org.eclipse.ui.workingSets extension point.
     * The extension is identified using the value returned by
     * <code>getId()</code>.
     * Returns <code>null</code> if no icon has been specified in the
     * extension or if <code>getId()</code> returns <code>null</code>.
     *
     * @return the working set icon or <code>null</code>.
     * @since 3.3
     */
    ImageDescriptor getImageDescriptor();

    /**
     * Returns the name of the working set.
     *
     * @return	the name of the working set
     */
    String getName();

    /**
