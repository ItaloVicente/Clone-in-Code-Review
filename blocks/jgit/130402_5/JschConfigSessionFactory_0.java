	private static void setPreferredKeyTypesOrder(Session session) {
		HostKeyRepository hkr = session.getHostKeyRepository();
		List<String> known = Stream.of(hkr.getHostKey(hostName(session)
				.map(HostKey::getType)
				.collect(toList());

		if (!known.isEmpty()) {
			String current = session.getConfig(serverHostKey);
			if (current == null) {
				session.setConfig(serverHostKey
	}

