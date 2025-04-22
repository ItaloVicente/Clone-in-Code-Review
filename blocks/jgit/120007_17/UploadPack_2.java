		List<ObjectId> shallowCommits = null;

		if (!clientShallowCommits.isEmpty()) {
			verifyClientShallow();
		}
		if (depth != 0 || shallowSince != 0 || shallowExcludeRefs != null) {
			shallowCommits = new ArrayList<ObjectId>();
			processShallow(shallowCommits);
		}
		if (!clientShallowCommits.isEmpty())
			walk.assumeShallow(clientShallowCommits);

