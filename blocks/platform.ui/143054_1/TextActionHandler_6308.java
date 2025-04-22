			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				activeTextControl.clearSelection();
				return;
			}
			if (deleteAction != null) {
				deleteAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(activeTextControl.getSelectionCount() > 0
						|| activeTextControl.getCaretPosition() < activeTextControl
								.getCharCount());
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
			super(CommonNavigatorMessages.Cut);
			setId("TextCutActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
					INavigatorHelpContextIds.TEXT_CUT_ACTION);
		}

		@Override
