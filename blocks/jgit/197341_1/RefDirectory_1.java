	RefList<Ref> getPackedRefs(Set<String> excludes) throws IOException {
		RefList<Ref> refs = getPackedRefs();
		if (!excludes.isEmpty()) {
			RefList.Builder<Ref> filteredRefs = new RefList.Builder<>();
			for (Ref r : refs) {
				if (excludes.stream().noneMatch(r.getName()::startsWith)) {
					filteredRefs.add(r);
				}
			}
			refs = filteredRefs.toRefList();
		}
		return refs;
	}

