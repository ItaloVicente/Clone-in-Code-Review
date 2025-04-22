
		int myType = getType();
		long mySize = getSize();
		final ObjectDirectoryInserter odi = db.newInserter();
		final File tmp = odi.newTempFile();
		DeflaterOutputStream dOut = odi.compress(new FileOutputStream(tmp));
		odi.writeHeader(dOut

		in = new TeeInputStream(in
		return new ObjectStream.Filter(myType
