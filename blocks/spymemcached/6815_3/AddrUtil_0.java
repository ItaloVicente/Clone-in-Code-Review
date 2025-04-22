
	public static List<InetSocketAddress> getAddresses(List<String> servers) {
		ArrayList<InetSocketAddress> addrs = new ArrayList<InetSocketAddress>();
		for (String server : servers) {
			int finalColon=server.lastIndexOf(':');
			if(finalColon < 1) {
				throw new IllegalArgumentException("Invalid server ``"
				+ server + "'' in list:  " + server);
		}
		String hostPart=server.substring(0, finalColon);
		String portNum=server.substring(finalColon+1);

		addrs.add(new InetSocketAddress(hostPart, Integer.parseInt(portNum)));
		}
		assert !addrs.isEmpty() : "No addrs found";
		return addrs;

	}
