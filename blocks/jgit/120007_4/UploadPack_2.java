		List<ObjectId> shallowCommits = null;

		if (!clientShallowCommits.isEmpty())
			verifyClientShallow();
		if (depth != 0) {
			shallowCommits = new ArrayList<ObjectId>();
			processShallow(shallowCommits);
		}
		if (!clientShallowCommits.isEmpty())
			walk.assumeShallow(clientShallowCommits);

