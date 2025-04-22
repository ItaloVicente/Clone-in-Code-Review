	public static OpenSshConfig get(FS fs) {
		File home = fs.userHome();
		if (home == null)
			home = new File(".").getAbsoluteFile();

		final File config = new File(new File(home, ".ssh"), "config");
		final OpenSshConfig osc = new OpenSshConfig(home, config);
		osc.refresh();
		return osc;
