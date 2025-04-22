			if (themes.length == 0) {
				globalStyles.add(uri);
			} else {
				for (String t : themes) {
					registerStyle(t, uri);
				}
