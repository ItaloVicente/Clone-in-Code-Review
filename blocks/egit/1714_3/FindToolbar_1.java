			prefsItem
					.setToolTipText(UIText.FindToolbar_changeto_id);
		}

		idItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				prefsItem.setImage(idIcon);
				prefsItem
						.setToolTipText(UIText.HistoryPage_findbar_changeto_commit);
				prefsItemChanged(PREFS_FINDIN_ID, idItem);
			}
		});
		if (selectedPrefsItem == PREFS_FINDIN_ID) {
			idItem.setSelection(true);
			prefsItem.setImage(idIcon);
