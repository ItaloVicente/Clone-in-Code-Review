			PendingCert pc
			throws IOException {
		Map<String
		if (matching != null) {
			byRef = new HashMap<>();
			for (ReceiveCommand cmd : matching) {
				byRef.put(cmd.getRefName()
			}
		} else {
			byRef = null;
		}

