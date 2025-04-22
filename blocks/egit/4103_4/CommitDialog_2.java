		Section pushSection = toolkit.createSection(container,
							ExpandableComposite.TITLE_BAR
							| ExpandableComposite.CLIENT_INDENT);
		pushSection.setText(UIText.CommitDialog_Push);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(pushSection);
		Composite pushArea = toolkit.createComposite(pushSection);
		pushSection.setClient(pushArea);
		toolkit.paintBordersFor(pushArea);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2).applyTo(pushArea);
		Button executePushButton = new Button(pushArea, SWT.CHECK);
		executePushButton.setText(UIText.CommitDialog_ExecutePush);
		executePushButton.setSelection(executePush);
		executePushButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				executePush = !executePush;
			}
		});

