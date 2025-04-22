		List<ObjectId> all = new ArrayList<ObjectId>(interestingObjects.size());
		for (ObjectId id : interestingObjects)
			all.add(id.copy());

		final Set<ObjectId> not;
		if (uninterestingObjects != null && !uninterestingObjects.isEmpty()) {
			not = new HashSet<ObjectId>();
			for (ObjectId id : uninterestingObjects)
				not.add(id.copy());
			all.addAll(not);
		} else
			not = Collections.emptySet();
