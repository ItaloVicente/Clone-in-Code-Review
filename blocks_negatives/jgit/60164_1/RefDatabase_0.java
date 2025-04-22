	public Ref exactRef(String name) throws IOException {
		Ref ref = getRef(name);
		if (ref == null || !name.equals(ref.getName())) {
			return null;
		}
		return ref;
	}
