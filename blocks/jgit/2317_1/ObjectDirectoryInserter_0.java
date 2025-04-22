		if (!in.markSupported())
			in = new BufferedInputStream(in);

		TransferConfig tc = db.getConfig().get(TransferConfig.KEY);
		long objectCount = PackParser.peekObjectCount(in);
		if (objectCount < tc.getUnpackLimit())
			return new UnpackingPackParser(db

