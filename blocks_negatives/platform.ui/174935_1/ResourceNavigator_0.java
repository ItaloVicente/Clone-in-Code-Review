		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < patterns.length; i++) {
			if (i != 0) {
				sb.append(ResourcePatternFilter.COMMA_SEPARATOR);
			}
			sb.append(patterns[i]);
		}

		getPlugin().getPreferenceStore().setValue(ResourcePatternFilter.FILTERS_TAG, sb.toString());
