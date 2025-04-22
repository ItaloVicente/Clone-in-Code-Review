	private Set<? extends ObjectId> union(Set<ObjectId> tags
		HashSet<ObjectId> unionSet = new HashSet<>(tags.size() + excludedRefsTips.size());
		unionSet.addAll(tags);
		unionSet.addAll(excludedRefsTips);
		return unionSet;
	}

