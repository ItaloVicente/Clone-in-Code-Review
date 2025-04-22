	private static void setPreferredKeyTypes(Session session) {
		HostKeyRepository hkr = session.getHostKeyRepository();
		List<String> keyTypes = new ArrayList<>();
		for (HostKey hk : hkr.getHostKey(
				hostName(session.getHost()
			keyTypes.add(hk.getType());
		}

		if (keyTypes.size() > 0) {
			String current = session.getConfig(serverHostKey);
			if (current == null) {
				session.setConfig(serverHostKey
	}

