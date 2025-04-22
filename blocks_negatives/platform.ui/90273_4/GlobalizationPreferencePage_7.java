		layoutDirectionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				layoutDirection = getLayoutDirectionInteger(layoutDirectionCombo
						.getSelectionIndex());
			}
		});
