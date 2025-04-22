			String smudgeFilterCommand = null;
			StreamSupplier fileStreamSupplier = null;
			ObjectId fileId = ObjectId.zeroId();
			if (walk == null) {
			} else if (inCore()) {
				fileId = walk.getObjectId(0);
				ObjectLoader loader = LfsFactory.getInstance()
						.applySmudgeFilter(repo, reader.open(fileId, OBJ_BLOB),
								null);
				byte[] data = loader.getBytes();
				convertCrLf = RawText.isCrLfText(data);
				fileStreamSupplier = () -> new ByteArrayInputStream(data);
				streamType = convertCrLf ? EolStreamType.TEXT_CRLF
						: EolStreamType.DIRECT;
				smudgeFilterCommand = walk
						.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
