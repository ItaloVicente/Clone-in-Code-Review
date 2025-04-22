		final ObjectWriter ow = new ObjectWriter(db);
		final ObjectId aSth = ow.writeBlob("a.sth".getBytes());
		final ObjectId aTxt = ow.writeBlob("a.txt".getBytes());
		final ObjectId bSth = ow.writeBlob("b.sth".getBytes());
		final ObjectId bTxt = ow.writeBlob("b.txt".getBytes());
		final DirCache dc = DirCache.read(db);
