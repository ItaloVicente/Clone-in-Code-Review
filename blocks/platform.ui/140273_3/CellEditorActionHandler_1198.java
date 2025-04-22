			if (activeEditor != null) {
				activeEditor.performPaste();
				return;
			}
			if (pasteAction != null) {
				pasteAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeEditor != null) {
				setEnabled(activeEditor.isPasteEnabled());
				return;
			}
			if (pasteAction != null) {
				setEnabled(pasteAction.isEnabled());
				return;
			}
			setEnabled(false);
		}
	}

	private class DeleteActionHandler extends Action {
		protected DeleteActionHandler() {
			setId("CellEditorDeleteActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_DELETE_ACTION);
		}

		@Override
