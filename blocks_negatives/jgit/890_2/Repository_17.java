	public List<ObjectId> readMergeHeads() throws IOException {
		File mergeHeadFile = new File(gitDir, Constants.MERGE_HEAD);
		byte[] raw;
		try {
			raw = IO.readFully(mergeHeadFile);
		} catch (FileNotFoundException notFound) {
			return new LinkedList<ObjectId>();
		}

		if (raw.length == 0)
			throw new IOException("MERGE_HEAD file empty: " + mergeHeadFile);

		LinkedList<ObjectId> heads = new LinkedList<ObjectId>();
		for (int p = 0; p < raw.length;) {
			heads.add(ObjectId.fromString(raw, p));
			p = RawParseUtils
					.nextLF(raw, p + Constants.OBJECT_ID_STRING_LENGTH);
		}
		return heads;
	}
