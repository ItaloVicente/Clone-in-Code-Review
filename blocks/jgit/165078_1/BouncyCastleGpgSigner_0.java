			PGPSignatureSubpacketGenerator subpackets = new PGPSignatureSubpacketGenerator();
			subpackets.setIssuerFingerprint(false
			String userId = committer.getEmailAddress();
			Iterator<String> userIds = publicKey.getUserIDs();
			if (userIds.hasNext()) {
				String keyUserId = userIds.next();
				if (!StringUtils.isEmptyOrNull(keyUserId)
						&& (userId == null || !keyUserId.contains(userId))) {
					userId = extractSignerId(keyUserId);
				}
			}
			if (userId != null) {
				subpackets.setSignerUserID(false
			}
