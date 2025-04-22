    /**
     * Tests that the view is closed without saving if isSaveOnCloseNeeded()
     * returns false. This also tests some disposal behaviors specific to
     * views: namely, that the contribution items are disposed in the correct
     * order with respect to the disposal of the view.
     *
     * @see ISaveablePart#isSaveOnCloseNeeded()
     */
    public void XXXtestOpenAndCloseSaveNotNeeded() throws Throwable {
        SaveableMockViewPart part = (SaveableMockViewPart) fPage.showView(SaveableMockViewPart.ID);
        part.setDirty(true);
        part.setSaveNeeded(false);
        closePart(fPage, part);
