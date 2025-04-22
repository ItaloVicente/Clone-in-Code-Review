	 * @return the working tree file to which the merged content was written.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private File writeMergedFile(TemporaryBuffer rawMerged,
			Attributes attributes)
			throws FileNotFoundException, IOException {
		File workTree = nonNullRepo().getWorkTree();
		FS fs = nonNullRepo().getFS();
		File of = new File(workTree, tw.getPathString());
		File parentFolder = of.getParentFile();
		if (!fs.exists(parentFolder)) {
			parentFolder.mkdirs();
		}
		EolStreamType streamType = EolStreamTypeUtil.detectStreamType(
				OperationType.CHECKOUT_OP, workingTreeOptions,
				attributes);
		try (OutputStream os = EolStreamTypeUtil.wrapOutputStream(
				new BufferedOutputStream(new FileOutputStream(of)),
				streamType)) {
			rawMerged.writeTo(os, null);
		}
		return of;
	}

	private TemporaryBuffer doMerge(MergeResult<RawText> result)
			throws IOException {
		TemporaryBuffer.LocalFile buf = new TemporaryBuffer.LocalFile(
				db != null ? nonNullRepo().getDirectory() : null, inCoreLimit);
		boolean success = false;
		try {
			new MergeFormatter().formatMerge(buf, result,
					Arrays.asList(commitNames), UTF_8);
			buf.close();
			success = true;
		} finally {
			if (!success) {
				buf.destroy();
			}
		}
		return buf;
	}

	private ObjectId insertMergeResult(TemporaryBuffer buf,
			Attributes attributes) throws IOException {
		InputStream in = buf.openInputStream();
		try (LfsInputStream is = LfsFactory.getInstance().applyCleanFilter(
				getRepository(), in,
				buf.length(), attributes.get(Constants.ATTR_MERGE))) {
			return getObjectInserter().insert(OBJ_BLOB, is.getLength(), is);
		}
	}

	/**
