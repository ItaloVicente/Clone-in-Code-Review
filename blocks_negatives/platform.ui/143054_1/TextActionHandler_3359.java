            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                activeTextControl.selectAll();
                return;
            }
            if (selectAllAction != null) {
                selectAllAction.runWithEvent(event);
                return;
            }
        }

        /**
         * Update the state.
         */
        public void updateEnabledState() {
            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                setEnabled(true);
                return;
            }
            if (selectAllAction != null) {
                setEnabled(selectAllAction.isEnabled());
                return;
            }
            setEnabled(false);
        }
    }

    /**
     * Creates a <code>Text</code> control action handler
     * for the global Cut, Copy, Paste, Delete, and Select All
     * of the action bar.
     *
     * @param actionBar the action bar to register global
     *    action handlers for Cut, Copy, Paste, Delete,
     * 	  and Select All
     */
    public TextActionHandler(IActionBars actionBar) {
        super();
        actionBar.setGlobalActionHandler(ActionFactory.CUT.getId(),
                textCutAction);
        actionBar.setGlobalActionHandler(ActionFactory.COPY.getId(),
                textCopyAction);
        actionBar.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                textPasteAction);
        actionBar.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
                textSelectAllAction);
        actionBar.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                textDeleteAction);
    }

    /**
     * Add a <code>Text</code> control to the handler
     * so that the Cut, Copy, Paste, Delete, and Select All
     * actions are redirected to it when active.
     *
     * @param textControl the inline <code>Text</code> control
     */
    public void addText(Text textControl) {
        if (textControl == null) {
			return;
