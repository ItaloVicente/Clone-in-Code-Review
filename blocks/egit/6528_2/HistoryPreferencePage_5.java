				showGroup));
		updateMargins(showGroup);
		Group commentGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.applyTo(commentGroup);
		commentGroup.setText(UIText.HistoryPreferencePage_ShowInRevCommentGroupLabel);
		addField(new BooleanFieldEditor(
				UIPreferences.HISTORY_SHOW_TAG_SEQUENCE,
				UIText.ResourceHistory_ShowTagSequence, commentGroup));
		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP,
				UIText.ResourceHistory_toggleCommentWrap,
				commentGroup));
		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_FILL,
				UIText.ResourceHistory_toggleCommentFill,
				commentGroup));
		updateMargins(commentGroup);
		adjustGridLayout();
	}

	private void updateMargins(Group group) {
		GridLayout layout = (GridLayout) group.getLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
