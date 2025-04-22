			PendingCert pc
			throws IOException {
		Map<String
		if (matching != null) {
			byRef = new HashMap<>();
			for (ReceiveCommand cmd : matching) {
				if (byRef.put(cmd.getRefName()
					throw new IllegalStateException();
				}
			}
		} else {
			byRef = null;
		}

