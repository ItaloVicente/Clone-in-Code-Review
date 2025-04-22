
		ObjectLoader loader = reader.open(id
		int threshold = PackConfig.DEFAULT_BIG_FILE_THRESHOLD;
		byte []data = RawText.openText(loader
		if (data == null) {
			throw new BinaryException();
		}
		return new RawText(data);
