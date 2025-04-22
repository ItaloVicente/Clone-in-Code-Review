    /**
     * Create a new instance of the receiver that is hooked to the current
     * display.
     *
     * @see org.eclipse.swt.widgets.Display#getCurrent()
     */
    public ColorRegistry() {
        this(Display.getCurrent(), true);
    }

    /**
     * Create a new instance of the receiver.
     *
     * @param display the <code>Display</code> to hook into.
     */
    public ColorRegistry(Display display) {
    	this (display, true);
    }

    /**
     * Create a new instance of the receiver.
     *
     * @param display the <code>Display</code> to hook into
     * @param cleanOnDisplayDisposal
