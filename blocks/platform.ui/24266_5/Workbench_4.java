
		PreferenceManager preferenceManager = (PreferenceManager) e4Context
				.get(PreferenceManager.class.getName());
		if (preferenceManager instanceof WorkbenchPreferenceManager) {
			((WorkbenchPreferenceManager) preferenceManager).dispose();
		}

