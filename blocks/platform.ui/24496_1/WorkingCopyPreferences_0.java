		for (Object key : temporarySettings.keySet()) {
			if (temporarySettings.get(key) != null) {
				allKeys.add(key);
			} else {
				allKeys.remove(key);
			}
		}
