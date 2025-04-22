	@Override
	@Nullable
	public String getGitwebDescription() throws IOException {
		String d;
		try {
			d = RawParseUtils.decode(IO.readFully(descriptionFile()));
		} catch (FileNotFoundException err) {
			return null;
		}
		if (d != null) {
			d = d.trim();
			if (d.isEmpty() || UNNAMED.equals(d)) {
				return null;
			}
		}
		return d;
	}

	@Override
	public void setGitwebDescription(@Nullable String description)
			throws IOException {
		String old = getGitwebDescription();
		if (Objects.equals(old
			return;
		}

		File path = descriptionFile();
		LockFile lock = new LockFile(path);
		if (!lock.lock()) {
			throw new IOException(MessageFormat.format(JGitText.get().lockError
					path.getAbsolutePath()));
		}
		try {
			String d = description;
			if (d != null) {
				d = d.trim();
				if (!d.isEmpty()) {
					d += '\n';
				}
			} else {
			}
			lock.write(Constants.encode(d));
			lock.commit();
		} finally {
			lock.unlock();
		}
	}

	private File descriptionFile() {
		return new File(getDirectory()
	}

