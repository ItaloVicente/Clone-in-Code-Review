		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			boolean retain = rw.isRetainBody();
			rw.setRetainBody(true);
			RevCommit r = rw.lookupCommit(fmt.idFor(Constants.OBJ_COMMIT
			r.parseCanonical(rw
			rw.setRetainBody(retain);
			return r;
		}
