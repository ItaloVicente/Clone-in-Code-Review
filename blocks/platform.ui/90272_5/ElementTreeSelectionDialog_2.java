            tree.addSelectionListener(widgetDefaultSelectedAdapter(e -> {
			    updateOKStatus();
			    if (fCurrStatus.isOK()) {
					access$superButtonPressed(IDialogConstants.OK_ID);
				}
			}));
