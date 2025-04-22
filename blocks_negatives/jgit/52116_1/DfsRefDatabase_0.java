	private Ref getOneRef(String refName) throws IOException {
		RefCache curr = read();
		Ref ref = curr.ids.get(refName);
		if (ref != null)
			return resolve(ref, 0, curr.ids);
		return ref;
	}

