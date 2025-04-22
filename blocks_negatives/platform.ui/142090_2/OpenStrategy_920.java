    /**
     * @param control the control the strategy is applied to
     */
    public OpenStrategy(Control control) {
        initializeHandler(control.getDisplay());
        addListener(control);
    }

    /**
     * Adds an IOpenEventListener to the collection of openEventListeners
     * @param listener the listener to add
     */
    public void addOpenListener(IOpenEventListener listener) {
        openEventListeners.add(listener);
    }

    /**
     * Removes an IOpenEventListener to the collection of openEventListeners
     * @param listener the listener to remove
     */
    public void removeOpenListener(IOpenEventListener listener) {
        openEventListeners.remove(listener);
    }

    /**
     * Adds an SelectionListener to the collection of selectionEventListeners
     * @param listener the listener to add
     */
    public void addSelectionListener(SelectionListener listener) {
        selectionEventListeners.add(listener);
    }

    /**
     * Removes an SelectionListener to the collection of selectionEventListeners
     * @param listener the listener to remove
     */
    public void removeSelectionListener(SelectionListener listener) {
        selectionEventListeners.remove(listener);
    }

    /**
     * Adds an SelectionListener to the collection of selectionEventListeners
     * @param listener the listener to add
     */
    public void addPostSelectionListener(SelectionListener listener) {
        postSelectionEventListeners.add(listener);
    }

    /**
     * Removes an SelectionListener to the collection of selectionEventListeners
     * @param listener the listener to remove
     */
    public void removePostSelectionListener(SelectionListener listener) {
        postSelectionEventListeners.remove(listener);
    }

    /**
     * This method is internal to the framework; it should not be implemented outside
     * the framework.
     * @return the current used single/double-click method
     *
     */
    public static int getOpenMethod() {
        return CURRENT_METHOD;
    }

    /**
     * Set the current used single/double-click method.
     *
     * This method is internal to the framework; it should not be implemented outside
     * the framework.
     * @param method the method to be used
     * @see OpenStrategy#DOUBLE_CLICK
     * @see OpenStrategy#SINGLE_CLICK
     * @see OpenStrategy#SELECT_ON_HOVER
     * @see OpenStrategy#ARROW_KEYS_OPEN
     */
    public static void setOpenMethod(int method) {
        if (method == DOUBLE_CLICK) {
            CURRENT_METHOD = method;
            return;
        }
        if ((method & SINGLE_CLICK) == 0) {
