
		Group g2 = new Group(main, SWT.NONE);
		g2.setText("FF"); //$NON-NLS-1$ // TODO
		GridDataFactory.fillDefaults().grab(true, false).applyTo(g2);
		g2.setLayout(new GridLayout(1, false));

		Button ff = new Button(g2, SWT.RADIO);
		ff.setSelection(true);
		ff.setText("ff");//$NON-NLS-1$ // TODO
		ff.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					fastForwardMode = FastForwardMode.FF;
			}
		});

		Button noff = new Button(g2, SWT.RADIO);
		noff.setSelection(true);
		noff.setText("no-ff");//$NON-NLS-1$ // TODO
		noff.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection())
					fastForwardMode = FastForwardMode.NO_FF;
			}
		});

		Button ffonly = new Button(g2, SWT.RADIO);
		ffonly.setSelection(true);
		ffonly.setText("ffonly");//$NON-NLS-1$ // TODO
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
