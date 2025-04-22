            if (activeEditor != null) {
                activeEditor.performCut();
                return;
            }
            if (cutAction != null) {
                cutAction.runWithEvent(event);
                return;
            }
        }

        public void updateEnabledState() {
            if (activeEditor != null) {
                setEnabled(activeEditor.isCutEnabled());
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
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_COPY_ACTION);
        }

        @Override
