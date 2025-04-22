		if (internalStore
				.contains(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS)) {
			apiStore
					.setValue(
							IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS,
							internalStore
							.getBoolean(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS));
			internalStore
					.setToDefault(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS);
