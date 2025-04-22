			findAction.setAccelerator(SWT.MOD1 | 'F');
			findAction.setEnabled(false);
			boolean isChecked = historyPage.store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_FINDTOOLBAR);
			findAction.setChecked(isChecked);
			historyPage.getSite().getActionBars().setGlobalActionHandler(
					ActionFactory.FIND.getId(), findAction);
			historyPage.getSite().getActionBars().getMenuManager()
					.update(false);
