		StringBuilder sentNonceBuilder = new StringBuilder();
		sentNonceBuilder.append(String.format("%d-"
		for (byte b : rawHmac) {
			sentNonceBuilder.append(String.format("%02x"
		}
		return sentNonceBuilder.toString();
