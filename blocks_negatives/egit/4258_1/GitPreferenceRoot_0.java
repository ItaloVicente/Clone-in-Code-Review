		Group historyGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		historyGroup.setText(UIText.GitPreferenceRoot_HistoryGroupHeader);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(historyGroup);

		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_RELATIVE_DATE,
				UIText.ResourceHistory_toggleRelativeDate, historyGroup));
		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_NOTES,
				UIText.ResourceHistory_toggleShowNotes, historyGroup));
		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP,
				UIText.ResourceHistory_toggleCommentWrap, historyGroup));

		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_REV_COMMENT,
				UIText.ResourceHistory_toggleRevComment, historyGroup));
		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_REV_DETAIL,
				UIText.ResourceHistory_toggleRevDetail, historyGroup));
		addField(new IntegerFieldEditor(UIPreferences.HISTORY_MAX_NUM_COMMITS,
				UIText.ResourceHistory_MaxNumCommitsInList, historyGroup));
		addField(new BooleanFieldEditor(
				UIPreferences.HISTORY_SHOW_TAG_SEQUENCE,
				UIText.ResourceHistory_ShowTagSequence, historyGroup));
		updateMargins(historyGroup);

