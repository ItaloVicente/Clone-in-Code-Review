	private String readBlob(RevCommit commit
			throws MissingObjectException
			CorruptObjectException
		TreeWalk tw = TreeWalk.forPath(db
		if (tw == null)
			return null;
		ObjectReader or = db.getObjectDatabase().newReader();
		ObjectId bId = tw.getObjectId(0);
		if (bId == null)
			return null;
		ObjectLoader ol = or.open(bId);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ol.copyTo(bos);
		or.release();
		return new String(bos.toByteArray()
	}

