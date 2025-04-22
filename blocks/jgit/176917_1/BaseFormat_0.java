
	protected int getCompressionLevel(Map<String
		if (!o.containsKey(COMPRESSION_LEVEL)) {
			return -1;
		}
		Object option = o.get(COMPRESSION_LEVEL);
		try {
			Integer compressionLevel = (Integer) option;
			return compressionLevel.intValue();
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(
					MessageFormat.format(
							ArchiveText.get().invalidCompressionLevel
					e);
		}
	}
