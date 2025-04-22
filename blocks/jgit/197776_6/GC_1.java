	private Set<? extends ObjectId> union(Set<ObjectId> tags
		HashSet<ObjectId> unionSet = new HashSet<>(tags.size() + excludedRefsHeadsTips.size());
		unionSet.addAll(tags);
		unionSet.addAll(excludedRefsHeadsTips);
		return unionSet;
	}

