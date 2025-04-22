		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		Label label = new Label(container, SWT.LEFT);
		label.setText(UIText.CommitDialog_CommitMessage);
		label.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).grab(true, false).create());

		commitText = new SpellcheckableMessageArea(container, commitMessage);
