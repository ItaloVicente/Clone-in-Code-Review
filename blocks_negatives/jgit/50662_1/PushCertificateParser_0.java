		try {
			return new PushCertificate(version, pusher, pushee, receivedNonce,
					nonceStatus, Collections.unmodifiableList(commands),
					rawCommands.toString(), signature);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage(), e);
		}
