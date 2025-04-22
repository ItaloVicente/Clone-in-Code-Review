    /**
     * Returns the CellData associated with the given control. If the control
     * does not have any layout data associated with it, a default object is returned.
     * If the control has a GridData object associated with it, an equivalent
     * CellData object will be returned.
     *
     * @param control
     * @return
     */
    static CellData getData(Control control) {
        Object layoutData = control.getLayoutData();
        CellData data = null;
