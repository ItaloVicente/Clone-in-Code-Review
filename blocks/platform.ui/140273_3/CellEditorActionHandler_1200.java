			if (activeEditor != null) {
				activeEditor.performSelectAll();
				return;
			}
			if (selectAllAction != null) {
				selectAllAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeEditor != null) {
				setEnabled(activeEditor.isSelectAllEnabled());
				return;
			}
			if (selectAllAction != null) {
				setEnabled(selectAllAction.isEnabled());
				return;
			}
			setEnabled(false);
		}
	}

	private class FindActionHandler extends Action {
		protected FindActionHandler() {
			setId("CellEditorFindActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IWorkbenchHelpContextIds.CELL_FIND_ACTION);
		}

		@Override
