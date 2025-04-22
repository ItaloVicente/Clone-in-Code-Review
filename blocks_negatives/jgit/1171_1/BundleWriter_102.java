	public void writeBundle(OutputStream os) throws IOException {
		final HashSet<ObjectId> inc = new HashSet<ObjectId>();
		final HashSet<ObjectId> exc = new HashSet<ObjectId>();
		inc.addAll(include.values());
		for (final RevCommit r : assume)
			exc.add(r.getId());
		packWriter.setThin(exc.size() > 0);
		packWriter.preparePack(inc, exc);
