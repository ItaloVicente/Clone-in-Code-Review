    /**
     * Saves the dialog settings.
     */
    private void saveDialogSettings() {
        IDialogSettings s = getDialogSettings();
        s.put(ALLPERSP, showAllPersp);
        s.put(SORT, sortColumn);
        bounds = getShell().getBounds();
        String array[] = new String[4];
        array[0] = String.valueOf(bounds.x);
        array[1] = String.valueOf(bounds.y);
        array[2] = String.valueOf(bounds.width);
        array[3] = String.valueOf(bounds.height);
        s.put(BOUNDS, array);
        array = new String[editorsTable.getColumnCount()];
        for (int i = 0; i < array.length; i++) {
