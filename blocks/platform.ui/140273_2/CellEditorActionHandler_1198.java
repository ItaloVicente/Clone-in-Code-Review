			if (activeEditor != null) {
				activeEditor.performFind();
				return;
			}
			if (findAction != null) {
				findAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeEditor != null) {
				setEnabled(activeEditor.isFindEnabled());
				return;
			}
			if (findAction != null) {
				setEnabled(findAction.isEnabled());
				return;
			}
			setEnabled(false);
		}
	}

	private class UndoActionHandler extends Action {
		protected UndoActionHandler() {
			setId("CellEditorUndoActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_UNDO_ACTION);
		}

		@Override
