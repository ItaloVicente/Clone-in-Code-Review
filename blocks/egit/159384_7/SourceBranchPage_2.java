	private void createTagOptionGroup(Composite parent) {
		final Group tagsGroup = new Group(parent, SWT.NULL);
		tagsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		tagsGroup.setText(UIText.TagOptions_groupName);
		tagsGroup.setLayout(new GridLayout());
		createTagOptionButton(tagsGroup, TagOpt.AUTO_FOLLOW,
				UIText.TagOptions_autoFollow);
		createTagOptionButton(tagsGroup, TagOpt.FETCH_TAGS,
				UIText.TagOptions_fetchTags);
		createTagOptionButton(tagsGroup, TagOpt.NO_TAGS,
				UIText.TagOptions_noTags);
	}

	private void createTagOptionButton(Group tagsGroup, final TagOpt option,
			String text) {
		Button button = new Button(tagsGroup, SWT.RADIO);
		button.setText(text);
		button.setSelection(option == tagOption);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tagOption = option;
			}
		});
	}

