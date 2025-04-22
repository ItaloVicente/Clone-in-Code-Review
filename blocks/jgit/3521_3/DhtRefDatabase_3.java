	private DhtRef getOneRef(String refName) throws IOException {
		RefCache curr = readRefs();
		DhtRef ref = curr.ids.get(refName);
		if (ref != null)
			return resolve(ref
		return ref;
	}

