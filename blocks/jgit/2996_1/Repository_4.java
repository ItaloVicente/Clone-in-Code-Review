
	public ObjectId readCherryPickHead() throws IOException
			NoWorkTreeException {
		if (isBare() || getDirectory() == null)
			throw new NoWorkTreeException();

		File mergeHeadFile = new File(getDirectory()
				Constants.CHERRY_PICK_HEAD);
		byte[] raw;
		try {
			raw = IO.readFully(mergeHeadFile);
		} catch (FileNotFoundException notFound) {
			return null;
		}

		if (raw.length == 0)
			return null;

		ObjectId head = ObjectId.fromString(raw
		return head;
	}

	public void writeCherryPickHead(ObjectId head) throws IOException {
		File cherryPickHeadFile = new File(gitDir
		if (head != null) {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(cherryPickHeadFile));
			try {
				head.copyTo(bos);
				bos.write('\n');
			} finally {
				bos.close();
			}
		} else {
			FileUtils.delete(cherryPickHeadFile
		}
	}
