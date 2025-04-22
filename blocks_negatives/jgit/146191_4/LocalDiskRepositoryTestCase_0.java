		if (!dir.exists())
			return silent;
		final File[] ls = dir.listFiles();
		if (ls != null)
			for (int k = 0; k < ls.length; k++) {
				final File e = ls[k];
				if (e.isDirectory())
					silent = recursiveDelete(e, silent, failOnError);
				else if (!e.delete()) {
					if (!silent)
						reportDeleteFailure(failOnError, e);
					silent = !failOnError;
				}
			}
		if (!dir.delete()) {
			if (!silent)
				reportDeleteFailure(failOnError, dir);
			silent = !failOnError;
