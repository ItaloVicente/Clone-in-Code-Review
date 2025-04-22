	private Collection<Ref> getAllRefs() throws IOException {
		Collection<Ref> refs = refdb.getRefs(RefDatabase.ALL).values();
		List<Ref> addl = refdb.getAdditionalRefs();
		if (!addl.isEmpty()) {
			List<Ref> all = new ArrayList<>(refs.size() + addl.size());
			all.addAll(refs);
			all.addAll(addl);
			return all;
		}
		return refs;
	}

