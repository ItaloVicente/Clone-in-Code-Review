
		Object oldValue = event.getOldValue();
		String pathSep = File.pathSeparator;

		if (oldValue != null) {
			String[] oldPaths = oldValue.toString().split(pathSep);
			List<String> removedPaths = new ArrayList<>(
					Arrays.asList(oldPaths));

			Object newValue = event.getNewValue();
			if (newValue != null) {
				String[] newPaths = newValue.toString().split(pathSep);
				for (String path : newPaths) {
					removedPaths.remove(path);
				}
			}

			for (String path : removedPaths) {
				unsetRepoSpecificPreference(path,
						UIPreferences.RESOURCEHISTORY_SHOW_FIRST_PARENT_ONLY_DEFAULT);
			}
			saveStoreIfNeeded();
		}
