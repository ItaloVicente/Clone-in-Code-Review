    /**
     * Initialize the action delegate by calling its lifecycle method.
     * Subclasses may override but must call this implementation first.
     */
    protected void initDelegate() {
        if (delegate instanceof IActionDelegate2) {
