	public void writeObjectSizeIndex(OutputStream objIdxStream)
			throws IOException {
		AsyncObjectSizeQueue<ObjectToPack> sizeQueue = reader.getObjectSize(
				objectsLists[OBJ_BLOB]
		try {
			while (sizeQueue.next()) {
				ObjectToPack otp = sizeQueue.getCurrent();
				long sz = sizeQueue.getSize();
				otp.setFullSize(sz);
			}
		} finally {
			sizeQueue.release();
		}
		PackObjectSizeIndexWriter iw = PackObjectSizeIndexWriter
				.createWriter(objIdxStream
						config.getMinBytesForObjSizeIndex());
		List<PackedObjectInfo> otps = new ArrayList<>(objectsLists[OBJ_BLOB]);
		iw.write(otps);
	}
