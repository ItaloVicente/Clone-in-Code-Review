            if (activeEditor != null) {
                activeEditor.performUndo();
                return;
            }
            if (undoAction != null) {
                undoAction.runWithEvent(event);
                return;
            }
        }

        public void updateEnabledState() {
            if (activeEditor != null) {
                setEnabled(activeEditor.isUndoEnabled());
                setText(WorkbenchMessages.Workbench_undo);
                setToolTipText(WorkbenchMessages.Workbench_undoToolTip);
                return;
            }
            if (undoAction != null) {
                setEnabled(undoAction.isEnabled());
                setText(undoAction.getText());
                setToolTipText(undoAction.getToolTipText());
                return;
            }
            setEnabled(false);
        }
    }

    private class RedoActionHandler extends Action {
        protected RedoActionHandler() {
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_REDO_ACTION);
        }

        @Override
