	private ObjectId insertMergeResult(MergeResult<RawText> result)
			throws IOException {
		TemporaryBuffer.LocalFile buf = new TemporaryBuffer.LocalFile(10 << 20);
		try {
			new MergeFormatter().formatMerge(buf
					Arrays.asList(commitNames)
			buf.close();
			return getObjectInserter().insert(OBJ_BLOB
					buf.openInputStream());
		} finally {
			buf.destroy();
		}
	}

