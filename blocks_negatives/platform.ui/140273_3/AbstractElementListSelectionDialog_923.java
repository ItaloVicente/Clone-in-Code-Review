        super(parent);
        fRenderer = renderer;
    }

    /**
     * Handles default selection (double click).
     * By default, the OK button is pressed.
     */
    protected void handleDefaultSelected() {
        if (validateCurrentSelection()) {
