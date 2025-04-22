			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				activeTextControl.copy();
				return;
			}
			if (copyAction != null) {
				copyAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(activeTextControl.getSelectionCount() > 0);
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
			super(CommonNavigatorMessages.Paste);
			setId("TextPasteActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
					INavigatorHelpContextIds.TEXT_PASTE_ACTION);
		}

		@Override
