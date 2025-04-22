		IPreferenceStore store = WorkbenchPlugin.getDefault().getPreferenceStore();
		boolean reuseEditors = store.getBoolean(IPreferenceConstants.REUSE_EDITORS_BOOLEAN);
		IContributionItem pinItem = toolBarManager.find(IWorkbenchCommandConstants.WINDOW_PIN_EDITOR);
		if (pinItem != null) {
			pinItem.setVisible(reuseEditors);
		}
		toolBarManager.markDirty();
