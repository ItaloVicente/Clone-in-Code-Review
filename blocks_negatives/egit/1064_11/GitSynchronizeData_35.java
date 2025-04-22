	/**
	 * @return source Tree
	 * @throws IOException
	 */
	public Tree mapSrcTree() throws IOException {
		return mapTree(srcRev);
	}

	/**
	 * @return destination Tree
	 * @throws IOException
	 */
	public Tree mapDstTree() throws IOException {
		return mapTree(dstRev);
	}

	/**
	 * @return source {@link ObjectId}
	 * @throws IOException
	 */
	public ObjectId getSrcObjectId() throws IOException {
		return getObjecId(srcRev);
	}

	/**
	 * @return destination {@link ObjectId}
	 * @throws IOException
	 */
	public ObjectId getDstObjectId() throws IOException {
		return getObjecId(dstRev);
	}

