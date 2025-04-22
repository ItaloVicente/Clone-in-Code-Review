            if (deleteAction != null) {
                deleteAction.runWithEvent(event);
                return;
            }
        }

        /**
         * Update state.
         */
        public void updateEnabledState() {
            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                setEnabled(activeTextControl.getEditable()
                		&& (activeTextControl.getSelectionCount() > 0
                        || activeTextControl.getCaretPosition() < activeTextControl
                                .getCharCount()));
                return;
            }
            if (deleteAction != null) {
                setEnabled(deleteAction.isEnabled());
                return;
            }
            setEnabled(false);
        }
    }

    private class CutActionHandler extends Action {
        protected CutActionHandler() {
            super(IDEWorkbenchMessages.Cut);
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
