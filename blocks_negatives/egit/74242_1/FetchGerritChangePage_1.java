		IDialogSettings s = Activator.getDefault().getDialogSettings();
		IDialogSettings section = s
				.getSection(FETCH_GERRIT_CHANGE_PAGE_SECTION);
		if (section == null)
			section = s.addNewSection(FETCH_GERRIT_CHANGE_PAGE_SECTION);
		return section;
