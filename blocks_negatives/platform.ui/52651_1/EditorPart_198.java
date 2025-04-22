    /* (non-Javadoc)
     * Initializes the editor part with a site and input.
     * <p>
     * Subclasses of <code>EditorPart</code> must implement this method.  Within
     * the implementation subclasses should verify that the input type is acceptable
     * and then save the site and input.  Here is sample code:
     * </p>
     * <pre>
     *		if (!(input instanceof IFileEditorInput))
     *			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
     *		setSite(site);
     *		setInput(input);
     * </pre>
     */
