		InputStream in = open(wc);
		in = new BufferedInputStream(in, 8192);

		int myType = getType();
		long mySize = getSize();
		final ObjectDirectoryInserter odi = db.newInserter();
		final File tmp = odi.newTempFile();
		DeflaterOutputStream dOut = odi.compress(new FileOutputStream(tmp));
		odi.writeHeader(dOut, myType, mySize);

		in = new TeeInputStream(in, dOut);
		return new ObjectStream.Filter(myType, mySize, in) {
