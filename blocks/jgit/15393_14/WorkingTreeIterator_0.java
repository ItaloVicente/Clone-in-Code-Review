	private String readContentAsString(DirCacheEntry entry)
			throws MissingObjectException
		ObjectLoader open = repository.open(entry.getObjectId());
		byte[] cachedBytes = open.getCachedBytes();
		return RawParseUtils.decode(cachedBytes);
	}

	private static String readContentAsString(Entry entry) throws IOException {
		long length = entry.getLength();
		byte[] content = new byte[(int) length];
		InputStream is = entry.openInputStream();
		IO.readFully(is
		return RawParseUtils.decode(content);
	}

