
	@Override
	protected PublicKeyIdentity resolveAttemptedPublicKeyIdentity(
			ClientSession session
		PublicKeyIdentity result = super.resolveAttemptedPublicKeyIdentity(
				session
		currentAlgorithms.clear();
		return result;
	}

