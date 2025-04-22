	}

	private long findDeltaBase(ObjectId baseId) throws IOException
			MissingObjectException {
		long ofs = idx().findOffset(baseId);
		if (ofs < 0)
			throw new MissingObjectException(baseId
					JGitText.get().missingDeltaBase);
		return ofs;
	}

	private PackedObjectLoader loadDelta(final long posData
			final long posBase
			DataFormatException {
		byte[] data;
		int type;

		UnpackedObjectCache.Entry e = readCache(posBase);
		if (e != null) {
			data = e.data;
			type = e.type;
		} else {
			PackedObjectLoader p = load(curs
			data = p.getCachedBytes();
			type = p.getType();
			saveCache(posBase
