	private void assertUntrackedFiles(String command
			boolean untrackedFiles) throws Exception {
		String[] output = new String[0];

		if (porcelain) {
			if (untrackedFiles) {
