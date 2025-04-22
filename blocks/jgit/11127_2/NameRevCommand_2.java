		for (Ref ref : repo.getRefDatabase().getRefs(prefix).values())
			addRef(ref
	}

	private void addRef(Ref ref
			FIFORevQueue pending) throws IOException {
		if (ref.getObjectId() == null)
			return;
		RevObject o = walk.parseAny(ref.getObjectId());
		while (o instanceof RevTag) {
			RevTag t = (RevTag) o;
			nonCommits.put(o
			o = t.getObject();
			walk.parseHeaders(o);
