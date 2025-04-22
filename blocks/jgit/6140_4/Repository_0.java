	public void writeOrigHead(ObjectId head) throws IOException {
		List<ObjectId> heads = head != null ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

	public ObjectId readOrigHead() throws IOException
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.ORIG_HEAD);
		return raw != null ? ObjectId.fromString(raw
	}

