		if (Activator.getDefault().getPreferenceStore().getBoolean(
				UIPreferences.RESOURCEHISTORY_REUSE_EDITOR))
			CompareUtils.openInCompare(site.getWorkbenchWindow().getActivePage(),
					in);
		else
			CompareUI.openCompareEditor(in);
