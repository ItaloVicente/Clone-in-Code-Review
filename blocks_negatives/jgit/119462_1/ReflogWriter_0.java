		FileOutputStream out;
		try {
			out = new FileOutputStream(log, true);
		} catch (FileNotFoundException err) {
			File dir = log.getParentFile();
			if (dir.exists())
				throw err;
			if (!dir.mkdirs() && !dir.isDirectory())
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotCreateDirectory, dir));
			out = new FileOutputStream(log, true);
		}
		try {
