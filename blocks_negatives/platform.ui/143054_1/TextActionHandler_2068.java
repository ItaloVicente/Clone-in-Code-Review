            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                activeTextControl.cut();
                updateActionsEnableState();
                return;
            }
            if (cutAction != null) {
                cutAction.runWithEvent(event);
                return;
            }
        }

        /**
         * Update state.
         */
        public void updateEnabledState() {
            if (activeTextControl != null && !activeTextControl.isDisposed()) {
                setEnabled(activeTextControl.getEditable() && activeTextControl.getSelectionCount() > 0);
                return;
            }
            if (cutAction != null) {
                setEnabled(cutAction.isEnabled());
                return;
            }
            setEnabled(false);
        }
    }

    private class CopyActionHandler extends Action {
        protected CopyActionHandler() {
            super(IDEWorkbenchMessages.Copy);
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
