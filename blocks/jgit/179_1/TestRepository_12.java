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

