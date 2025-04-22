		File mergeHeadFile = new File(getDirectory(), Constants.MERGE_HEAD);
		byte[] raw;
		try {
			raw = IO.readFully(mergeHeadFile);
		} catch (FileNotFoundException notFound) {
			return null;
		}

		if (raw.length == 0)
