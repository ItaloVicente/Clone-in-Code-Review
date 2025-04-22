		public void updateEnabledState() {
			if (activeEditor != null) {
				setEnabled(activeEditor.isRedoEnabled());
				setText(WorkbenchMessages.Workbench_redo);
				setToolTipText(WorkbenchMessages.Workbench_redoToolTip);
				return;
			}
			if (redoAction != null) {
				setEnabled(redoAction.isEnabled());
				setText(redoAction.getText());
				setToolTipText(redoAction.getToolTipText());
				return;
			}
			setEnabled(false);
