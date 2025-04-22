		List<ObjectId> heads = (head == null) ? null : Arrays.asList(head);
		writeHeadsFile(heads
	}

	private byte[] readGitDirectoryFile(String filename) throws IOException {
		File file = new File(getDirectory()
		byte[] raw;
		try {
			raw = IO.readFully(file);
		} catch (FileNotFoundException notFound) {
			return null;
		}

		if (raw.length == 0)
			return null;

		return raw;
	}

	private void writeHeadsFile(List<ObjectId> heads
			throws FileNotFoundException
		File headsFile = new File(getDirectory()
		if (heads != null) {
