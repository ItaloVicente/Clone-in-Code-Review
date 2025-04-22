
		Group fastForwardGroup = new Group(main, SWT.NONE);
		fastForwardGroup
				.setText(UIText.MergeTargetSelectionDialog_FastForwardGroup);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(fastForwardGroup);
		fastForwardGroup.setLayout(new GridLayout(1, false));

		createFastForwardButton(fastForwardGroup,
				UIText.MergeTargetSelectionDialog_FastForwardButton,
				FastForwardMode.FF);
		createFastForwardButton(fastForwardGroup,
				UIText.MergeTargetSelectionDialog_NoFastForwardButton,
				FastForwardMode.NO_FF);
		createFastForwardButton(fastForwardGroup,
				UIText.MergeTargetSelectionDialog_OnlyFastForwardButton,
				FastForwardMode.FF_ONLY);
	}

	private void createFastForwardButton(Group grp, String text,
			final FastForwardMode ffMode) {
		Button btn = new Button(grp, SWT.RADIO);
		btn.setText(text);
		btn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					fastForwardMode = ffMode;
			}
		});
		btn.setSelection(fastForwardMode == ffMode);
