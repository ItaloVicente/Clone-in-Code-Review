			menuItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					fKeySequenceText.insert(trappedKey);
					fBindingText.setFocus();
					fBindingText.setSelection(fBindingText.getTextLimit());
				}
			});
