	private Protocol getProtocol() {
		int idx = scheme.getSelectionIndex();
		if (idx >= 0)
			return Protocol.values()[idx];
		return null;
	}

