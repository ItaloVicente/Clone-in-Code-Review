		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		File mergeMsgFile = new File(getDirectory(), Constants.MERGE_MSG);
		try {
			return RawParseUtils.decode(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
