		isGerritRepo = isGerritRepo();
		GridLayout buttonsLayout = (GridLayout) parent.getLayout();
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(parent);
		pushSettings = new PushSettings(repository);
		Control pushControl = pushSettings.createControl(parent);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(pushControl);
		if (isGerritRepo && commitMessageComponent.getCreateChangeId()) {
			pushSettings.setVisible(false);
		}
		Composite buttonsContainer = new Composite(parent, SWT.NONE);
		buttonsContainer.setLayout(buttonsLayout);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(buttonsContainer);
		toolkit.adapt(buttonsContainer, false, false);
		commitAndPushButton = createButton(buttonsContainer, COMMIT_AND_PUSH_ID,
