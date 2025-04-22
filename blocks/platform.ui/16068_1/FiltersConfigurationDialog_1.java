	private void createMarkerSystemLinks(Composite parent) {

		if (generator.isSuppressSymbolicLinksVisible()) {
			symbolicLinkButton = new Button(parent, SWT.CHECK);
			symbolicLinkButton.setText(MarkerMessages.MarkerPreferences_SymbolicLinks);
			symbolicLinkButton.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {
					generator.setSuppressSymbolicLinksChecked(symbolicLinkButton.getSelection());
				}
			});
		}
	}

