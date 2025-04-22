
	protected int removeAndGetCompressionLevel(Map<String
		if (!o.containsKey(COMPRESSION_LEVEL)) {
			return -1;
		}
		Object option = o.get(COMPRESSION_LEVEL);
		try {
			Integer compressionLevel = (Integer) option;
			o.remove(COMPRESSION_LEVEL);
			return compressionLevel.intValue();
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Invalid compression level: " + option
		}
	}
