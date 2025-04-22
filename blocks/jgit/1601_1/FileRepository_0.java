		final boolean fileMode;
		if (getFS().supportsExecute()) {
			File tmp = File.createTempFile("try"

			getFS().setExecute(tmp
			final boolean on = getFS().canExecute(tmp);

			getFS().setExecute(tmp
			final boolean off = getFS().canExecute(tmp);
			tmp.delete();

			fileMode = on && !off;
		} else {
			fileMode = false;
		}

