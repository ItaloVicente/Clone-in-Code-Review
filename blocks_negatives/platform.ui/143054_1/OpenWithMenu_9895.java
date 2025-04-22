        this(page, null);
    }

    /**
     * Constructs a new instance of <code>OpenWithMenu</code>.
     *
     * @param page the page where the editor is opened if an item within
     *		the menu is selected
     * @param file the selected file
     */
    public OpenWithMenu(IWorkbenchPage page, IAdaptable file) {
        super(ID);
        this.page = page;
