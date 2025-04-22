			sb.append(patterns[i]);
		}

		getPlugin().getPreferenceStore().setValue(ResourcePatternFilter.FILTERS_TAG, sb.toString());

		IPreferenceStore preferenceStore = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		String storedPatterns = preferenceStore.getString(ResourcePatternFilter.FILTERS_TAG);
		if (storedPatterns.length() > 0) {
