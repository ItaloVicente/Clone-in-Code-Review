
		commentsItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prefsItem.setImage(commentsIcon);
				prefsItem
						.setToolTipText(UIText.HistoryPage_findbar_changeto_author);
				prefsItemChanged(PREFS_FINDIN_AUTHOR, commentsItem);
			}
		});
		if (selectedPrefsItem == PREFS_FINDIN_COMMENTS) {
			commentsItem.setSelection(true);
			prefsItem.setImage(commentsIcon);
			prefsItem
					.setToolTipText(UIText.HistoryPage_findbar_changeto_author);
		}

		authorItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prefsItem.setImage(authorIcon);
				prefsItem
						.setToolTipText(UIText.HistoryPage_findbar_changeto_commit);
				prefsItemChanged(PREFS_FINDIN_COMMITID, authorItem);
			}
		});
		if (selectedPrefsItem == PREFS_FINDIN_AUTHOR) {
			authorItem.setSelection(true);
			prefsItem.setImage(authorIcon);
			prefsItem
					.setToolTipText(UIText.HistoryPage_findbar_changeto_commit);
		}

		commitIdItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prefsItem.setImage(commitIdIcon);
				prefsItem
						.setToolTipText(UIText.HistoryPage_findbar_changeto_committer);
				prefsItemChanged(PREFS_FINDIN_COMMITTER, commitIdItem);
			}
		});
		if (selectedPrefsItem == PREFS_FINDIN_COMMITID) {
			commitIdItem.setSelection(true);
			prefsItem.setImage(commitIdIcon);
			prefsItem
					.setToolTipText(UIText.HistoryPage_findbar_changeto_committer);
		}

		committerItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prefsItem.setImage(committerIcon);
				prefsItem
						.setToolTipText(UIText.HistoryPage_findbar_changeto_comments);
				prefsItemChanged(PREFS_FINDIN_COMMENTS, committerItem);
			}
		});
		if (selectedPrefsItem == PREFS_FINDIN_COMMITTER) {
			committerItem.setSelection(true);
			prefsItem.setImage(committerIcon);
			prefsItem
					.setToolTipText(UIText.HistoryPage_findbar_changeto_comments);
		}
