    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    public void setFocus() {
        final boolean odds = (number % 2) > 0;
        IKeyBindingService keyBindingService = getEditorSite()
                .getKeyBindingService();
        keyBindingService.setScopes(new String[] { (odds) ? scope1 : scope2 });
    }
