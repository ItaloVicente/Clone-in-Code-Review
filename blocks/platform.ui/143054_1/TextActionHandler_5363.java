			if (deleteAction != null) {
				deleteAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(activeTextControl.getEditable()
						&& (activeTextControl.getSelectionCount() > 0
						|| activeTextControl.getCaretPosition() < activeTextControl
								.getCharCount()));
				return;
			}
			if (deleteAction != null) {
				setEnabled(deleteAction.isEnabled());
				return;
			}
			setEnabled(false);
		}
	}

	private class CutActionHandler extends Action {
		protected CutActionHandler() {
			super(IDEWorkbenchMessages.Cut);
			setId("TextCutActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
