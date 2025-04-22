		File path = new File(relPath);
		try {
			return IO.readFully(path);
		} catch (FileNotFoundException fnfe) {
			if (path.exists()) {
				throw new ConfigInvalidException(MessageFormat
						.format(JGitText.get().cannotReadFile, path), fnfe);
			}
			return null;
		} catch (IOException ioe) {
			throw new ConfigInvalidException(
					MessageFormat.format(JGitText.get().cannotReadFile, path),
					ioe);
		}
