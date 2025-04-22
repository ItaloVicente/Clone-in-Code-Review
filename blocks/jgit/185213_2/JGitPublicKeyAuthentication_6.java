		JGitClientSession session = ((JGitClientSession) rawSession);
		HostConfigEntry hostConfig = session.getHostConfigEntry();
		String pubkeyAlgos = hostConfig.getProperty(PUBKEY_ACCEPTED_ALGORITHMS);
		if (!StringUtils.isEmptyOrNull(pubkeyAlgos)) {
			List<String> signatures = session.getSignatureFactoriesNames();
			signatures = session.modifyAlgorithmList(signatures
					session.getAllAvailableSignatureAlgorithms()
					PUBKEY_ACCEPTED_ALGORITHMS);
			if (!signatures.isEmpty()) {
