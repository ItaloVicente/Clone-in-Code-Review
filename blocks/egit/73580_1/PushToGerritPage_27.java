		useTopic = new Button(main, SWT.CHECK | SWT.LEFT);
		useTopic.setText(UIText.PushToGerritPage_TopicUseLabel);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				.applyTo(useTopic);
		topicLabel = new Label(main, SWT.NONE);
		topicLabel.setText(UIText.PushToGerritPage_TopicLabel);
		topicText = new Text(main, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.applyTo(topicText);
		topicText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				checkPage();
			}
		});
		topicLabel.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				topicText.setFocus();
				topicText.selectAll();
			}
		});

		useTopic.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				topicText.setEnabled(useTopic.getSelection());
				checkPage();
			}
		});

