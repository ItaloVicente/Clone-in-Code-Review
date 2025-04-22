	public File gitPrefix() {
		Holder<File> p = gitPrefix;
		if (p == null) {
			p = new Holder<File>(discoverGitPrefix());
			gitPrefix = p;
		}
		return p.value;
	}

	protected abstract File discoverGitPrefix();

	public FS setGitPrefix(File path) {
		gitPrefix = new Holder<File>(path);
		return this;
	}
