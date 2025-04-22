
	public RevObject lookupAny(final AnyObjectId id) {
		RevObject r = objects.get(id);
		if (r == null)
			r = new RevBlob(id);
		return r;
	}


