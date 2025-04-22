	@Override
	public Ref exactRef(String name) throws IOException {
		RefCache curr = read();
		Ref ref = curr.ids.get(name);
		return ref != null ? resolve(ref, 0, curr.ids) : null;
	}

