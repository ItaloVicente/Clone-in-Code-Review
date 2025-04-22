	private Collection<Ref> getAllRefs() throws IOException {
		Collection<Ref> refs = RefTreeNames.allRefs(repo.getRefDatabase());
		List<Ref> addl = repo.getRefDatabase().getAdditionalRefs();
		if (!addl.isEmpty()) {
			List<Ref> all = new ArrayList<>(refs.size() + addl.size());
			all.addAll(refs);
			all.addAll(addl);
			return all;
		}
		return refs;
