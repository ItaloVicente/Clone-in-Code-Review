		addField(editor);

		Group historyGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		historyGroup.setText(UIText.GitPreferenceRoot_HistoryGroupHeader);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(historyGroup);

		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP,
				UIText.ResourceHistory_toggleCommentWrap, historyGroup));

		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_REV_COMMENT,
				UIText.ResourceHistory_toggleRevComment, historyGroup));
		addField(new BooleanFieldEditor(
				UIPreferences.RESOURCEHISTORY_SHOW_REV_DETAIL,
				UIText.ResourceHistory_toggleRevDetail, historyGroup));
		updateMargins(historyGroup);

		Group remoteConnectionsGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(remoteConnectionsGroup);
		remoteConnectionsGroup
				.setText(UIText.GitPreferenceRoot_RemoteConnectionsGroupHeader);

		IntegerFieldEditor timeoutEditor = new IntegerFieldEditor(
				UIPreferences.REMOTE_CONNECTION_TIMEOUT,
				UIText.RemoteConnectionPreferencePage_TimeoutLabel,
				remoteConnectionsGroup);
		timeoutEditor.getLabelControl(remoteConnectionsGroup).setToolTipText(
				UIText.RemoteConnectionPreferencePage_ZeroValueTooltip);
		addField(timeoutEditor);
		updateMargins(remoteConnectionsGroup);

		Group repoChangeScannerGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(repoChangeScannerGroup);
		repoChangeScannerGroup
				.setText(UIText.GitPreferenceRoot_RepoChangeScannerGroupHeader);
		addField(new BooleanFieldEditor(UIPreferences.REFESH_ON_INDEX_CHANGE,
				UIText.RefreshPreferencesPage_RefreshWhenIndexChange,
				repoChangeScannerGroup));
		addField(new BooleanFieldEditor(UIPreferences.REFESH_ONLY_WHEN_ACTIVE,
				UIText.RefreshPreferencesPage_RefreshOnlyWhenActive,
				repoChangeScannerGroup));
		updateMargins(repoChangeScannerGroup);
	}

	private void updateMargins(Group group) {
		GridLayout layout = (GridLayout) group.getLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
