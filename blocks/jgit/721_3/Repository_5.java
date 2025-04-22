
	public String readMergeCommitMsg() throws IOException {
		File mergeMsgFile = new File(gitDir
		try {
			return new String(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public List<ObjectId> readMergeHeads() throws IOException {
		List<ObjectId> heads = null;
		File mergeHeadFile = new File(gitDir
		if (mergeHeadFile.exists()) {
			try {
				FileReader r = new FileReader(mergeHeadFile);
				BufferedReader mh = new BufferedReader(r);
				String headStr;
				try {
					heads = new LinkedList<ObjectId>();
					while ((headStr = mh.readLine()) != null) {
						heads.add(ObjectId.fromString(headStr));
					}
				} finally {
					r.close();
				}

			} catch (FileNotFoundException e) {
				heads = null;
			}
		}
		return heads;
	}
