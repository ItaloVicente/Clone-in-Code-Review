	private RevObject peelTag(RevWalk rw
			throws MissingObjectException
		while (ref instanceof RevTag)
			ref = rw.parseAny(((RevTag) ref).getObject());
		return ref;
	}

