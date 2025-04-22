					if (((Button) event.widget).getSelection()) {
						fsPathText.setEnabled(true);
						fsBrowseButton.setEnabled(true);
						fsPathText.setFocus();
						validatePage();
					}
