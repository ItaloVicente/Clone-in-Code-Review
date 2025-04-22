	private boolean isSameHosts(final String name1, final String name2) {

		if (hostname1.equals(hostname2))
			return true;

		String resolvedHostName;
		try {
			resolvedHostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ex) {
			resolvedHostName = localHost;
