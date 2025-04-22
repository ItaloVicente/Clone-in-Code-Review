            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                activeTextControl.paste();
                return;
            }
            if (pasteAction != null) {
                pasteAction.runWithEvent(event);
                return;
            }
        }

        /**
         * Update the state
         */
        public void updateEnabledState() {
            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                setEnabled(true);
                return;
            }
            if (pasteAction != null) {
                setEnabled(pasteAction.isEnabled());
                return;
            }
            setEnabled(false);
        }
    }

    private class SelectAllActionHandler extends Action {
        protected SelectAllActionHandler() {
            super(CommonNavigatorMessages.TextAction_selectAll);
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
