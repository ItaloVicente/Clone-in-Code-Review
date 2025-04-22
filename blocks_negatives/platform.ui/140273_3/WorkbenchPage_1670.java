    /**
     * Constructs a page. <code>restoreState(IMemento)</code> should be
     * called to restore this page from data stored in a persistance file.
     *
     * @param w
     *            the parent window
     * @param input
     *            the page input
     * @throws WorkbenchException
     */
    public WorkbenchPage(WorkbenchWindow w, IAdaptable input)
            throws WorkbenchException {
        super();
        init(w, null, input, false);
    }

    /**
     * Allow access to the UI model that this page is managing
     * @return the MWindow element for this page
     */
    public MWindow getWindowModel() {
    	return window;

    }
    /**
     * Activates a part. The part will be brought to the front and given focus.
     *
     * @param part
     *            the part to activate
     */
    @Override
