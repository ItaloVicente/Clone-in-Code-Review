		RevWalk walk = new RevWalk(repo);
		for (Ref ref : revTags) {
			try {
				tags.add(walk.parseTag(repo.resolve(ref.getName())));
			} catch (IOException e) {
				throw new ExecutionException(e.getMessage(), e);
