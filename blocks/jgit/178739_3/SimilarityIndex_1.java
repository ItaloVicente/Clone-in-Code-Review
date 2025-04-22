	static boolean isBinary(ObjectLoader obj) throws IOException {
		if (obj.isLarge()) {
			try (ObjectStream in1 = obj.openStream()) {
				return RawText.isBinary(in1);
			}
		}
		return RawText.isBinary(obj.getCachedBytes());
	}

