    /**
     * Sets the size to use for the window's shell when it is created.
     * This method has no effect after the shell is created.
     * That is, it must be called within the <code>preWindowOpen</code>
     * callback on <code>WorkbenchAdvisor</code>.
     *
     * @param initialSize the initial size to use for the shell
     */
    void setInitialSize(Point initialSize);
