			fsBrowseButton.setEnabled(false);

			cpRadio.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					fsPathText.setEnabled(false);
					fsBrowseButton.setEnabled(false);
					validatePage();
				}
			});
