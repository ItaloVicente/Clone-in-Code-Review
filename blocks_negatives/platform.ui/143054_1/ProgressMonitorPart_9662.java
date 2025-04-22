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
