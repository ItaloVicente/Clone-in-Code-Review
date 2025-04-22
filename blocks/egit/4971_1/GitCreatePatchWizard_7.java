			wsRadio.addListener(SWT.Selection, new Listener() {

				public void handleEvent(Event event) {
					if (((Button) event.widget).getSelection()) {
						fsPathText.setEnabled(false);
						fsBrowseButton.setEnabled(false);
						wsPathText.setEnabled(true);
						wsBrowseButton.setEnabled(true);
						wsPathText.setFocus();
						validatePage();
					}
				}
			});

			wsPathText.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					if (validatePage()) {
						IPath filePath= Path.fromOSString(wsPathText.getText()).removeLastSegments(1);
						getDialogSettings().put(PATH_KEY, filePath.toPortableString());
					}
				}
			});

			wsBrowseButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					final WorkspaceDialog dialog = new WorkspaceDialog(getShell());
					wsBrowsed = true;
					dialog.open();
					validatePage();
				}
			});

