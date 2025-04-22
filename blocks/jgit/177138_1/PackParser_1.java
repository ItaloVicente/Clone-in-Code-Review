	private void checkBlobs() throws IOException {
		for (PackedObjectInfo blob : wholeBlobsPendingCheck) {
			BlobObjectChecker checker = null;
			if (objCheck != null) {
				checker = objCheck.newBlobObjectChecker();
			}
			if (checker == null) {
				checker = BlobObjectChecker.NULL_CHECKER;
			}

			ObjectTypeAndSize info = openDatabase(blob
					new ObjectTypeAndSize());
			final byte[] readBuffer = buffer();
			long sz = info.size;

			try (InputStream pck = inflate(Source.DATABASE
				while (0 < sz) {
					int n = (int) Math.min(readBuffer.length
					IO.readFully(pck
					checker.update(readBuffer
					sz -= n;
				}
				checker.endBlob(blob);
			}
		}
	}

