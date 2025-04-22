        return parser.getTitle();
    }

    /**
     * Returns the intro item or <code>null</code>
     */
    private WelcomeItem getIntroItem() {
        return parser.getIntroItem();
    }

    /**
     * Returns the welcome items
     */
    private WelcomeItem[] getItems() {
        return parser.getItems();
    }

    /**
     * Sets the cursor and selection state for this editor to the passage defined
     * by the given marker.
     * <p>
     * Subclasses may override.  For greater details, see <code>IEditorPart</code>
     * </p>
     *
     * @see IEditorPart
     */
    public void gotoMarker(IMarker marker) {
    }

    /**
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
     *		setInput(editorInput);
     * </pre>
     */
    @Override
