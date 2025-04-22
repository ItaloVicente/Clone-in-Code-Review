		commitIdItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prefsItem.setImage(commitIdIcon);
				prefsItem
						.setToolTipText(UIText.HistoryPage_findbar_changeto_comments);
				prefsItemChanged(PREFS_FINDIN_COMMITID, commitIdItem);
			}
		});
		if (selectedPrefsItem == PREFS_FINDIN_COMMITID) {
			commitIdItem.setSelection(true);
			prefsItem.setImage(commitIdIcon);
			prefsItem
					.setToolTipText(UIText.HistoryPage_findbar_changeto_comments);
		}

