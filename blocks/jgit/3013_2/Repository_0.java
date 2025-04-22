		List<ObjectId> heads = (head != null) ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

	private byte[] readGitDirectoryFile(String filename) throws IOException {
		File file = new File(getDirectory()
		try {
			byte[] raw = IO.readFully(file);
			return raw.length > 0 ? raw : null;
		} catch (FileNotFoundException notFound) {
			return null;
		}
	}

	private void writeHeadsFile(List<ObjectId> heads
			throws FileNotFoundException
		File headsFile = new File(getDirectory()
		if (heads != null) {
