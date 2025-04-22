	public void testLeafSplitsWhenFull() throws Exception {
		RevBlob data1 = tr.blob("data1");
		MutableObjectId idBuf = new MutableObjectId();

				.add(data1.name()
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		for (int i = 0; i < 254; i++) {
			idBuf.setByte(Constants.OBJECT_ID_LENGTH - 1
			map.set(idBuf
		}

		RevCommit n = commitNoteMap(map);
		TreeWalk tw = new TreeWalk(reader);
		tw.reset(n.getTree());
		while (tw.next())
			assertFalse("no fan-out subtree"

		for (int i = 254; i < 256; i++) {
			idBuf.setByte(Constants.OBJECT_ID_LENGTH - 1
			map.set(idBuf
		}
		idBuf.setByte(Constants.OBJECT_ID_LENGTH - 2
		map.set(idBuf
		n = commitNoteMap(map);

		String path = fanout(38
		tw = TreeWalk.forPath(reader
		assertNotNull("has " + path

		path = fanout(2
		tw = TreeWalk.forPath(reader
		assertNotNull("has " + path
	}

