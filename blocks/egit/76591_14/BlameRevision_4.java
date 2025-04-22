	private static RawText readText(Repository db, AbbreviatedObjectId blobId,
			ObjectReader reader, String path) throws IOException {
		ObjectLoader oldLoader = LfsHelper.getSmudgeFiltered(db,
				reader.open(blobId.toObjectId(), Constants.OBJ_BLOB),
				LfsHelper.getAttributesForPath(db, path)
						.get(Constants.ATTR_DIFF));
