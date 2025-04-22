
	public String readMergeCommitMsg() throws IOException {
		File mergeMsgFile = new File(gitDir
		if (mergeMsgFile.exists()) {
			int length = (int) mergeMsgFile.length();
			char[] messageBuffer = new char[length];
			try {
				FileReader r = new FileReader(mergeMsgFile);
				r.read(messageBuffer
				r.close();
				return new String(messageBuffer);
			} catch (FileNotFoundException e) {
			}
		}
		return null;
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
