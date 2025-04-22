			menuItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					textTriggerSequenceManager.insert(trappedKey);
					textTriggerSequence.setFocus();
					textTriggerSequence.setSelection(textTriggerSequence
							.getTextLimit());
				}
			});
