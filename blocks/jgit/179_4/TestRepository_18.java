	public void fsck(RevObject... tips) throws MissingObjectException
			IncorrectObjectTypeException
		ObjectWalk ow = new ObjectWalk(db);
		if (tips.length != 0) {
			for (RevObject o : tips)
				ow.markStart(ow.parseAny(o));
		} else {
			for (Ref r : db.getAllRefs().values())
				ow.markStart(ow.parseAny(r.getObjectId()));
		}

		ObjectChecker oc = new ObjectChecker();
		for (;;) {
			final RevCommit o = ow.next();
			if (o == null)
				break;

			final byte[] bin = db.openObject(o).getCachedBytes();
			oc.checkCommit(bin);
			assertHash(o
		}

		for (;;) {
			final RevObject o = ow.nextObject();
			if (o == null)
				break;

			final byte[] bin = db.openObject(o).getCachedBytes();
			oc.check(o.getType()
			assertHash(o
		}
	}

	private static void assertHash(RevObject id
		MessageDigest md = Constants.newMessageDigest();
		md.update(Constants.encodedTypeString(id.getType()));
		md.update((byte) ' ');
		md.update(Constants.encodeASCII(bin.length));
		md.update((byte) 0);
		md.update(bin);
		Assert.assertEquals(id.copy()
	}

	public void packAndPrune() throws Exception {
		final ObjectDirectory odb = (ObjectDirectory) db.getObjectDatabase();
		final PackWriter pw = new PackWriter(db

		Set<ObjectId> all = new HashSet<ObjectId>();
		for (Ref r : db.getAllRefs().values())
			all.add(r.getObjectId());
		pw.preparePack(all

		final ObjectId name = pw.computeName();
		FileOutputStream out;

		final File pack = nameFor(odb
		out = new FileOutputStream(pack);
		try {
			pw.writePack(out);
		} finally {
			out.close();
		}
		pack.setReadOnly();

		final File idx = nameFor(odb
		out = new FileOutputStream(idx);
		try {
			pw.writeIndex(out);
		} finally {
			out.close();
		}
		idx.setReadOnly();

		odb.openPack(pack
		updateServerInfo();
		prunePacked(odb);
	}

	private void prunePacked(ObjectDirectory odb) {
		for (PackFile p : odb.getPacks()) {
			for (MutableEntry e : p)
				odb.fileFor(e.toObjectId()).delete();
		}
	}

	private static File nameFor(ObjectDirectory odb
		File packdir = new File(odb.getDirectory()
		return new File(packdir
	}

	private void writeFile(final String name
			throws IOException
		final File p = new File(db.getDirectory()
		final LockFile lck = new LockFile(p);
		if (!lck.lock())
			throw new ObjectWritingException("Can't write " + p);
		try {
			lck.write(bin);
		} catch (IOException ioe) {
			throw new ObjectWritingException("Can't write " + p);
		}
		if (!lck.commit())
			throw new ObjectWritingException("Can't write " + p);
	}

