		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			RevCommit r = rw.lookupCommit(fmt.idFor(Constants.OBJ_COMMIT
			r.parseCanonical(rw
			r.buffer = raw;
			return r;
		}
