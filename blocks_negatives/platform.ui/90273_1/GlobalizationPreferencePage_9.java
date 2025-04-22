		textDirectionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textDirection = getTextDirectionString(textDirectionCombo.getSelectionIndex());
			}
		});
