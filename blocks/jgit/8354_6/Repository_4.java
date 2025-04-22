	public ObjectId readRevertHead() throws IOException
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		byte[] raw = readGitDirectoryFile(Constants.REVERT_HEAD);
		if (raw == null)
			return null;
		return ObjectId.fromString(raw
	}

