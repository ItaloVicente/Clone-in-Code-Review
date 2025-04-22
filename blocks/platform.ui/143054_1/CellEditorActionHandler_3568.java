			if (activeEditor != null) {
				activeEditor.performDelete();
				return;
			}
			if (deleteAction != null) {
				deleteAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeEditor != null) {
				setEnabled(activeEditor.isDeleteEnabled());
				return;
			}
			if (deleteAction != null) {
				setEnabled(deleteAction.isEnabled());
				return;
			}
			setEnabled(false);
		}
	}

	private class SelectAllActionHandler extends Action {
		protected SelectAllActionHandler() {
			setId("CellEditorSelectAllActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_SELECT_ALL_ACTION);
		}

		@Override
