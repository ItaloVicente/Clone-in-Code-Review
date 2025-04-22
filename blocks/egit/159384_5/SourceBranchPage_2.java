	private void createTagOptionGroup(Composite parent) {
		final Group tagsGroup = new Group(parent, SWT.NULL);
		tagsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		tagsGroup.setText("tag fetching strategy"
		);
		tagsGroup.setLayout(new GridLayout(3, false));
		createTagOptionButton(tagsGroup, TagOpt.AUTO_FOLLOW,
				"Fetch tags on tracked branches",
				UIText.RefSpecPage_annotatedTagsAutoFollow);
		createTagOptionButton(tagsGroup, TagOpt.FETCH_TAGS, "Fetch all tags",
				UIText.RefSpecPage_annotatedTagsFetchTags);
		createTagOptionButton(tagsGroup, TagOpt.NO_TAGS, "Fetch no tags",
				UIText.RefSpecPage_annotatedTagsNoTags);
	}

	private void createTagOptionButton(Group tagsGroup, final TagOpt option,
			String text, String tooltip) {
		Button button = new Button(tagsGroup, SWT.RADIO);
		button.setText(text);
		button.setToolTipText(tooltip);
		button.setSelection(option == tagOption);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tagOption = option;
			}
		});
	}

