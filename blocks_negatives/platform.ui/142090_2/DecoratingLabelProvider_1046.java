        super.removeListener(listener);
        provider.removeListener(listener);
        if (decorator != null) {
            decorator.removeListener(listener);
        }
        listeners.remove(listener);
    }

    /**
     * Sets the label decorator.
     * Removes all known listeners from the old decorator, and adds all known listeners to the new decorator.
     * The old decorator is not disposed.
     * Fires a label provider changed event indicating that all labels should be updated.
     * Has no effect if the given decorator is identical to the current one.
     *
     * @param decorator the label decorator, or <code>null</code> if no decorations are to be applied
     */
    public void setLabelDecorator(ILabelDecorator decorator) {
        ILabelDecorator oldDecorator = this.decorator;
        if (oldDecorator != decorator) {
            if (oldDecorator != null) {
