        Control control = getLabelControl();
        if (control != null) {
            ((GridData) control.getLayoutData()).horizontalSpan = numColumns;
        }
        ((GridData) radioBox.getLayoutData()).horizontalSpan = numColumns;
    }

    /**
     * Checks whether given <code>String[][]</code> is of "type"
     * <code>String[][2]</code>.
     * @param table
     *
     * @return <code>true</code> if it is ok, and <code>false</code> otherwise
     */
    private boolean checkArray(String[][] table) {
        if (table == null) {
