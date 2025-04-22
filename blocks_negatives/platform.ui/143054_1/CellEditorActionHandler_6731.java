            if (activeEditor == null) {
				return;
			}
            if (event.getProperty().equals(CellEditor.CUT)) {
                cellCutAction.setEnabled(activeEditor.isCutEnabled());
                return;
            }
            if (event.getProperty().equals(CellEditor.COPY)) {
                cellCopyAction.setEnabled(activeEditor.isCopyEnabled());
                return;
            }
            if (event.getProperty().equals(CellEditor.PASTE)) {
                cellPasteAction.setEnabled(activeEditor.isPasteEnabled());
                return;
            }
            if (event.getProperty().equals(CellEditor.DELETE)) {
                cellDeleteAction.setEnabled(activeEditor.isDeleteEnabled());
                return;
            }
            if (event.getProperty().equals(CellEditor.SELECT_ALL)) {
                cellSelectAllAction.setEnabled(activeEditor
                        .isSelectAllEnabled());
                return;
            }
            if (event.getProperty().equals(CellEditor.FIND)) {
                cellFindAction.setEnabled(activeEditor.isFindEnabled());
                return;
            }
            if (event.getProperty().equals(CellEditor.UNDO)) {
                cellUndoAction.setEnabled(activeEditor.isUndoEnabled());
                return;
            }
            if (event.getProperty().equals(CellEditor.REDO)) {
                cellRedoAction.setEnabled(activeEditor.isRedoEnabled());
                return;
            }
        }
    }

    private class CutActionHandler extends Action {
        protected CutActionHandler() {
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_CUT_ACTION);
        }

        @Override
