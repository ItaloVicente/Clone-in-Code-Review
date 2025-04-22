    /**
     * Sets the display to execute the asyncExec in. Generally this is not'
     * used if there is a valid display available via PlatformUI.isWorkbenchRunning().
     *
     * @param runDisplay
     *            Display
     * @see UIJob#getDisplay()
     * @see PlatformUI#isWorkbenchRunning()
     */
    public void setDisplay(Display runDisplay) {
        Assert.isNotNull(runDisplay);
        cachedDisplay = runDisplay;
    }
