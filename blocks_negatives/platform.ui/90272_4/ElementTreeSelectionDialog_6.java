            tree.addSelectionListener(new SelectionAdapter() {
                @Override
				public void widgetDefaultSelected(SelectionEvent e) {
                    updateOKStatus();
                    if (fCurrStatus.isOK()) {
						access$superButtonPressed(IDialogConstants.OK_ID);
					}
                }
            });
