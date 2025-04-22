		amendingItem.addSelectionListener(new SelectionAdapter() {
			boolean alreadyAdded = false;
			public void widgetSelected(SelectionEvent arg0) {
				if (!amendingItem.getSelection()) {
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
					if (previousAuthor != null)
						authorText.setText(previousAuthor);
				}
				refreshChangeIdText();
			}
		});
