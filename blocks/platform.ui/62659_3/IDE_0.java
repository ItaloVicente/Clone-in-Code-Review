	private static IUnknownEditorStrategy getUnknowEditorStrategy() {
		String preferedStrategy = IDEWorkbenchPlugin.getDefault().getPreferenceStore()
				.getString(UNKNOWN_EDITOR_STRATEGY_PREFERENCE_KEY);
		IUnknownEditorStrategy res = UnknownEditorStrategyRegistry.getStrategy(preferedStrategy);
		if (res == null) {
			res = new SystemEditorOrTextEditor();
		}
		return res;
	}

