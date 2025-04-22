
	@Override
	protected PublicKeyIdentity resolveAttemptedPublicKeyIdentity(
			ClientSession session, String service) throws Exception {
		PublicKeyIdentity result = super.resolveAttemptedPublicKeyIdentity(
				session, service);
		currentAlgorithms.clear();
		return result;
	}

