	private void createTagOptionGroup(Composite parent) {
		final Group tagsGroup = new Group(parent, SWT.NULL);
		tagsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		tagsGroup.setText(UIText.RefSpecPage_annotatedTagsGroup);
		tagsGroup.setLayout(new GridLayout());
		createTagOptionButton(tagsGroup, TagOpt.AUTO_FOLLOW,
				UIText.RefSpecPage_annotatedTagsAutoFollow);
		createTagOptionButton(tagsGroup, TagOpt.FETCH_TAGS,
				UIText.RefSpecPage_annotatedTagsFetchTags);
		createTagOptionButton(tagsGroup, TagOpt.NO_TAGS,
				UIText.RefSpecPage_annotatedTagsNoTags);
	}

	private void createTagOptionButton(Group tagsGroup, final TagOpt option,
			String text) {
		Button button = new Button(tagsGroup, SWT.RADIO);
		button.setText(text);
		button.setSelection(option == tagOpion);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tagOpion = option;
			}
		});
	}

