	public void writeObjectSizeIndex(OutputStream objIdxStream)
			throws IOException {
		if (config.getMinBytesForObjSizeIndex() < 0) {
			return;
		}

		long writeStart = System.currentTimeMillis();
		AsyncObjectSizeQueue<ObjectToPack> sizeQueue = reader
				.getObjectSize(objectsLists[OBJ_BLOB]
		try {
			while (sizeQueue.next()) {
				ObjectToPack otp = sizeQueue.getCurrent();
				long sz = sizeQueue.getSize();
				otp.setFullSize(sz);
			}
		} finally {
			sizeQueue.release();
		}
		PackObjectSizeIndexWriter iw = PackObjectSizeIndexWriter.createWriter(
				objIdxStream
		iw.write(sortByName());
		stats.timeWriting += System.currentTimeMillis() - writeStart;
	}

