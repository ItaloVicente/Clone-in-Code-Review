	private void writeHeader() throws IOException {
		System.arraycopy(Constants.PACK_SIGNATURE, 0, buf, 0, 4);
		NB.encodeInt32(buf, 4, PACK_VERSION_GENERATED);
		NB.encodeInt32(buf, 8, getObjectsNumber());
		out.write(buf, 0, 12);
	}

