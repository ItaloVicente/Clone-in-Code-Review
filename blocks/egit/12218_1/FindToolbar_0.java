		allItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prefsItem.setImage(allIcon);
				prefsItem
						.setToolTipText(UIText.HistoryPage_findbar_changeto_comments);
				prefsItemChanged(PREFS_FINDIN_COMMENTS, allItem);
			}
		});
		if (selectedPrefsItem == PREFS_FINDIN_ALL) {
			allItem.setSelection(true);
			prefsItem.setImage(allIcon);
			prefsItem
					.setToolTipText(UIText.HistoryPage_findbar_changeto_comments);
		}

