		return new RefMap(prefix
	}

	private RefList<Ref> filterRefsWithExclusions(RefList<Ref> refs
		if (!excludes.isEmpty()) {
			RefList.Builder<Ref> filteredRefs = new RefList.Builder<>();
			for (Ref r : refs) {
				if (excludes.stream().noneMatch(r.getName()::startsWith)) {
					filteredRefs.add(r);
				}
			}
			return filteredRefs.toRefList();
		}
		return refs;
	}

	@Override
	public List<Ref> getRefsByPrefixWithExclusions(String prefix
			throws IOException {
		return new ArrayList<>(getRefs(prefix
