		italicViaFontStyleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				useItalicViaFontStyleAttribute = italicViaFontStyleButton.getSelection();
				tableViewer.refresh();
			}
		});

