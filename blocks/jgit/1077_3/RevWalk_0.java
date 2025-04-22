	public RevTag lookupTag(final AnyObjectId id) {
		RevTag c = (RevTag) objects.get(id);
		if (c == null) {
			c = new RevTag(id);
			objects.add(c);
		}
		return c;
	}

