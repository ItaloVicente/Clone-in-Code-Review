			public void checkStateChanged(CheckStateChangedEvent event) {
				if (fExistingDirectories.contains(event.getElement()))
					event.getCheckable().setChecked(event.getElement(), false);
				getButton(IDialogConstants.OK_ID).setEnabled(
						fTreeViewer.getCheckedElements().length > 0);
			}
