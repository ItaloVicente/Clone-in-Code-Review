	public ReflogReader getReflogReader(String refName) throws IOException {
		Ref ref = getRef(refName);
		if (ref != null)
			return new ReflogReader(this, ref.getName());
		return null;
	}
