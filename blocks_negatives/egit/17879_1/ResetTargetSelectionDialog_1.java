		Button medium = new Button(g, SWT.RADIO);
		medium.setSelection(true);
		medium.setText(UIText.ResetTargetSelectionDialog_ResetTypeMixedButton);
		medium.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					resetType = ResetType.MIXED;
			}
		});
