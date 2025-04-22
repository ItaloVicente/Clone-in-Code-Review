	private MenuItem createFindInMenuItem() {
		final MenuItem menuItem = new MenuItem(prefsMenu, SWT.RADIO);
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selectFindInItem(menuItem);
			}
		});
		return menuItem;
	}

	private void selectFindInItem(final MenuItem menuItem) {
		if (menuItem == allItem)
			selectFindInItem(menuItem, PREFS_FINDIN_ALL, allIcon,
					UIText.HistoryPage_findbar_changeto_comments);
		else if (menuItem == commentsItem)
			selectFindInItem(menuItem, PREFS_FINDIN_COMMENTS, commentsIcon,
					UIText.HistoryPage_findbar_changeto_author);
		else if (menuItem == authorItem)
			selectFindInItem(menuItem, PREFS_FINDIN_AUTHOR, authorIcon,
					UIText.HistoryPage_findbar_changeto_commit);
		else if (menuItem == commitIdItem)
			selectFindInItem(menuItem, PREFS_FINDIN_COMMITID, commitIdIcon,
					UIText.HistoryPage_findbar_changeto_committer);
		else if (menuItem == committerItem)
			selectFindInItem(menuItem, PREFS_FINDIN_COMMITTER, committerIcon,
					UIText.HistoryPage_findbar_changeto_all);
	}

	private void selectFindInItem(MenuItem menuItem, int preferenceValue,
			Image dropDownIcon, String dropDownToolTip) {
		prefsDropDown.setImage(dropDownIcon);
		prefsDropDown.setToolTipText(dropDownToolTip);
		findInPreferenceChanged(preferenceValue, menuItem);
	}

	private void findInPreferenceChanged(int findin, MenuItem item) {
