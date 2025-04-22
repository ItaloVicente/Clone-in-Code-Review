		if (!dir.exists())
			return silent;
		final File[] ls = dir.listFiles();
		if (ls != null) {
			for (File f : ls) {
				if (f.isDirectory()) {
					silent = recursiveDelete(f, silent, failOnError);
				} else if (!f.delete()) {
					if (!silent) {
						reportDeleteFailure(failOnError, f);
					}
					silent = !failOnError;
				}
			}
