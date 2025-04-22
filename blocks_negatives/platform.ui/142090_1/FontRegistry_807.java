    /**
     * Creates an empty font registry.
     * <p>
     * There must be an SWT Display created in the current
     * thread before calling this method.
     * </p>
     */
    public FontRegistry() {
    	this(Display.getCurrent(), true);
    }

    /**
