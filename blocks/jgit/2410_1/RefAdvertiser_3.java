	public Set<ObjectId> send(Map<String
		for (Ref ref : getSortedRefs(refs)) {
			if (ref.getObjectId() == null)
				continue;

			advertiseAny(ref.getObjectId()

			if (!derefTags)
				continue;

			if (!ref.isPeeled()) {
				if (repository == null)
					continue;
				ref = repository.peel(ref);
