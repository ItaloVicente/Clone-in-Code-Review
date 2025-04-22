            if (activeEditor != null) {
                activeEditor.performCopy();
                return;
            }
            if (copyAction != null) {
                copyAction.runWithEvent(event);
                return;
            }
        }

        public void updateEnabledState() {
            if (activeEditor != null) {
                setEnabled(activeEditor.isCopyEnabled());
                return;
            }
            if (copyAction != null) {
                setEnabled(copyAction.isEnabled());
                return;
            }
            setEnabled(false);
        }
    }

    private class PasteActionHandler extends Action {
        protected PasteActionHandler() {
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_PASTE_ACTION);
        }

        @Override
