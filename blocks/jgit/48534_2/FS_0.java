	public File gitPrefix() {
		Holder<File> p = gitPrefix;
		if (p == null) {
			String overrideGitPrefix = SystemReader.getInstance().getProperty(
			if (overrideGitPrefix != null)
				p = new Holder<File>(new File(overrideGitPrefix));
			else
				p = new Holder<File>(discoverGitPrefix());
			gitPrefix = p;
		}
		return p.value;
	}

