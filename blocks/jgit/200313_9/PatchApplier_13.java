		TreeWalk walk = getTreeWalkForFile(pathWithOriginalContent
		boolean loadedFromTreeWalk = false;
		boolean convertCrLf = inCore() || needsCrLfConversion(f
		EolStreamType streamType = convertCrLf ? EolStreamType.TEXT_CRLF
				: EolStreamType.DIRECT;
		String smudgeFilterCommand = null;
		StreamSupplier fileStreamSupplier = null;
		ObjectId fileId = ObjectId.zeroId();
		if (walk == null) {
		} else if (inCore()) {
			fileId = walk.getObjectId(0);
			ObjectLoader loader = LfsFactory.getInstance()
					.applySmudgeFilter(repo
							null);
			byte[] data = loader.getBytes();
			convertCrLf = RawText.isCrLfText(data);
			fileStreamSupplier = () -> new ByteArrayInputStream(data);
			streamType = convertCrLf ? EolStreamType.TEXT_CRLF
