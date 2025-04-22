	public static RevCommit parse(byte[] raw) {
		return parse(new RevWalk((ObjectReader) null)
	}

	public static RevCommit parse(RevWalk rw
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		boolean retain = rw.isRetainBody();
		rw.setRetainBody(true);
		RevCommit r = rw.lookupCommit(fmt.idFor(Constants.OBJ_COMMIT
		r.parseCanonical(rw
		rw.setRetainBody(retain);
		return r;
	}

