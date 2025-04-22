		int[] weights = UIPreferences.stringToIntArray(store.getString(key), 2);
		if (weights == null) {
			weights = UIPreferences
					.stringToIntArray(store.getDefaultString(key), 2);
		}
		sf.setWeights(weights);
