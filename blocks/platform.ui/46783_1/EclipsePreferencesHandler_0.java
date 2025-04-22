		boolean force = false;
		if (isDefaultPreferencesProp) {
			String absolutePath = preferences.absolutePath();
			if (absolutePath.indexOf(INITIAL_INSTANCE_PREFERENCES_PATH) == 0) {
				String path = absolutePath.substring(INITIAL_INSTANCE_PREFERENCES_PATH.length());
				preferences = DefaultScope.INSTANCE.getNode(path);

				force = true;
			}
		}

