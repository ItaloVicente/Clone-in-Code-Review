	private boolean isSameHosts(String name1, String name2) {
		String hostname1 = name1 == null ? LOCALHOST : name1.trim();
		String hostname2 = name2 == null ? LOCALHOST : name2.trim();
		if (hostname1.isEmpty()) {
			hostname1 = LOCALHOST;
		}
		if (hostname2.isEmpty()) {
			hostname2 = LOCALHOST;
