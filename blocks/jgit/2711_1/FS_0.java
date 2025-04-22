		Holder<File> p = userHome;
		if (p == null) {
			p = new Holder<File>(userHomeImpl());
			userHome = p;
		}
		return p.value;
	}

	public FS setUserHome(File path) {
		userHome = new Holder<File>(path);
		return this;
