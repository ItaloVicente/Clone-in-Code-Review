		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			boolean retain = rw.isRetainBody();
			rw.setRetainBody(true);
			RevTag r = rw.lookupTag(fmt.idFor(Constants.OBJ_TAG
			r.parseCanonical(rw
			rw.setRetainBody(retain);
			return r;
		}
