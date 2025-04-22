		PublicKeyIdentity result = getNextKey(session, service);
		currentAlgorithms.clear();
		return result;
	}

	private PublicKeyIdentity getNextKey(ClientSession session, String service)
			throws Exception {
