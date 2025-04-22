
		Group fastForwardGroup = new Group(main, SWT.NONE);
		fastForwardGroup
				.setText(UIText.MergeTargetSelectionDialog_FastForwardGroup);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(fastForwardGroup);
		fastForwardGroup.setLayout(new GridLayout(1, false));

		Button ff = new Button(fastForwardGroup, SWT.RADIO);
		ff.setText(UIText.MergeTargetSelectionDialog_FastForwardButton);
		ff.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					fastForwardMode = FastForwardMode.FF;
			}
		});

		Button noff = new Button(fastForwardGroup, SWT.RADIO);
		noff.setText(UIText.MergeTargetSelectionDialog_NoFastForwardButton);
		noff.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					fastForwardMode = FastForwardMode.NO_FF;
			}
		});

		Button ffonly = new Button(fastForwardGroup, SWT.RADIO);
		ffonly.setText(UIText.MergeTargetSelectionDialog_OnlyFastForwardButton);
		ffonly.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					fastForwardMode = FastForwardMode.FF_ONLY;
			}
		});

		ff.setSelection(false);
		noff.setSelection(false);
		ffonly.setSelection(false);
		switch (fastForwardMode) {
		case FF:
			ff.setSelection(true);
			break;
		case NO_FF:
			noff.setSelection(true);
			break;
		case FF_ONLY:
			ffonly.setSelection(true);
			break;
		}
