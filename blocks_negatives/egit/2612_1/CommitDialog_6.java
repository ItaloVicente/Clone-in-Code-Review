		amendingButton = new Button(container, SWT.CHECK);
		if (amending) {
			amendingButton.setSelection(amending);
			authorText.setText(previousAuthor);
			saveOriginalChangeId();
		} else if (!amendAllowed) {
			amendingButton.setEnabled(false);
			originalChangeId = null;
		}
		amendingButton.addSelectionListener(new SelectionListener() {
			boolean alreadyAdded = false;
			public void widgetSelected(SelectionEvent arg0) {
				if (!amendingButton.getSelection()) {
					originalChangeId = null;
					authorText.setText(author);
				}
				else {
					saveOriginalChangeId();
					if (!alreadyAdded) {
						alreadyAdded = true;
						commitText.setText(previousCommitMessage.replaceAll(
								"\n", Text.DELIMITER)); //$NON-NLS-1$
					}
					authorText.setText(previousAuthor);
				}
				refreshChangeIdText();
			}
