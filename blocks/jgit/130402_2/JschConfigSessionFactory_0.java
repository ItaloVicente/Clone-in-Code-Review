	private static void setPreferredKeyTypes(Session session
			int port) {
		HostKeyRepository hkr = session.getHostKeyRepository();
		List<String> keyTypes = new ArrayList<>();
		for (HostKey hk : hkr.getHostKey(hostName(host
			keyTypes.add(hk.getType());
		}

		if (keyTypes.size() > 0) {
			if (current == null) {
				session.setConfig("server_host_key"
						String.join("
	}

