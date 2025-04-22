        return selectionListener;
    }

    /**
     * Returns this field editor's shell.
     * <p>
     * This method is internal to the framework; subclassers should not call
     * this method.
     * </p>
     *
     * @return the shell
     */
    protected Shell getShell() {
        if (addButton == null) {
