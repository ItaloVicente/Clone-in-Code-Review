		LooseItems loose = null;
		try {
			loose = FileUtils.readWithRetries(path
					f -> new LooseItems(FileSnapshot.save(f)
							IO.readSome(f
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(
					MessageFormat.format(JGitText.get().cannotReadFile
					e);
		}
		if (loose == null) {
			return null;
		}
		int n = loose.buf.length;
