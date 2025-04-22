    /**
     * Create a new instance of this class.
     *
     * @param parent the parent registry
     */
    public CascadingFontRegistry(FontRegistry parent) {
    	super(Display.getCurrent(), false);
        this.parent = parent;
        parent.addListener(listener);
    }
