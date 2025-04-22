	private String sentNonce() {
		if (sentNonce == null && nonceGenerator != null) {
			sentNonce = nonceGenerator.createNonce(db
					TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
		}
		return sentNonce;
	}

	private static String parseHeader(PacketLineIn pckIn
