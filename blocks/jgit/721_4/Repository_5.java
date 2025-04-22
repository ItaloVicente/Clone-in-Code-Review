
	public String readMergeCommitMsg() throws IOException {
		File mergeMsgFile = new File(gitDir
		try {
			return new String(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public List<ObjectId> readMergeHeads() throws IOException {
		File mergeHeadFile = new File(gitDir
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
			heads.add(ObjectId.fromString(raw
			p = RawParseUtils
					.nextLF(raw
		}
		return heads;
	}
