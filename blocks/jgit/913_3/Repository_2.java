		return ref != null ? ref.copy() : resolveSimple(revstr);
	}

	private RevObject parseSimple(RevWalk rw
		ObjectId id = resolveSimple(revstr);
		return id != null ? rw.parseAny(id) : null;
