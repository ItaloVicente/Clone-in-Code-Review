		String pubkeyAlgos = hostConfig
				.getProperty(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS);
		if (!StringUtils.isEmptyOrNull(pubkeyAlgos)) {
			List<String> signatures = getSignatureFactoriesNames();
			signatures = session.modifyAlgorithmList(signatures, pubkeyAlgos,
					SshConstants.PUBKEY_ACCEPTED_ALGORITHMS);
			if (!signatures.isEmpty()) {
				if (log.isDebugEnabled()) {
					log.debug(SshConstants.PUBKEY_ACCEPTED_ALGORITHMS + ' '
							+ signatures);
				}
				session.setSignatureFactoriesNames(signatures);
			} else {
				log.warn(format(SshdText.get().configNoKnownAlgorithms,
						SshConstants.PUBKEY_ACCEPTED_ALGORITHMS, pubkeyAlgos));
			}
		}
