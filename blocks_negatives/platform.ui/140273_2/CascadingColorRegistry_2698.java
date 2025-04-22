    /**
     * Create a new instance of this class.
     *
     * @param parent the parent registry
     */
    public CascadingColorRegistry(ColorRegistry parent) {
    	super(Display.getCurrent(), false);
        this.parent = parent;
        parent.addListener(listener);
    }
