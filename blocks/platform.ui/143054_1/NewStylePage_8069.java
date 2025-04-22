		titleTextSelectableButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				form.getForm().setTitleTextSelectable(titleTextSelectableButton.getSelection());
			}
		});
