    /**
     * Implements <code>IProgressMonitor.beginTask</code>.
     * @see IProgressMonitor#beginTask(java.lang.String, int)
     */
    public void beginTask(String name, int totalWork) {
        fTaskName = name;
        updateLabel();
        if (totalWork == IProgressMonitor.UNKNOWN || totalWork == 0) {
            fProgressIndicator.beginAnimatedTask();
        } else {
            fProgressIndicator.beginTask(totalWork);
        }
        if (fToolBar != null && !fToolBar.isDisposed()) {
        	fToolBar.setVisible(true);
        	fToolBar.setFocus();
        }
    }

    /**
     * Implements <code>IProgressMonitor.done</code>.
     * @see IProgressMonitor#done()
     */
    public void done() {
        fProgressIndicator.sendRemainingWork();
        fProgressIndicator.done();
        if (fToolBar != null && !fToolBar.isDisposed())
        	fToolBar.setVisible(false);
    }

    /**
     * Escapes any occurrence of '&' in the given String so that
     * it is not considered as a mnemonic
     * character in SWT ToolItems, MenuItems, Button and Labels.
     * @param in the original String
     * @return The converted String
     */
    protected static String escapeMetaCharacters(String in) {
        if (in == null || in.indexOf('&') < 0) {
