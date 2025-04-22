		return writeObject(type, len, is, false);
	}

	ObjectId writeObject(final int type, long len, final InputStream is,
			boolean store) throws IOException {
		final File t;
		final DeflaterOutputStream deflateStream;
		final FileOutputStream fileStream;
		ObjectId id = null;
		Deflater def = null;

		if (store) {
			t = File.createTempFile("noz", null, r.getObjectsDirectory());
			fileStream = new FileOutputStream(t);
		} else {
			t = null;
			fileStream = null;
		}

		md.reset();
		if (store) {
			def = new Deflater(r.getConfig().getCore().getCompression());
			deflateStream = new DeflaterOutputStream(fileStream, def);
		} else
			deflateStream = null;

