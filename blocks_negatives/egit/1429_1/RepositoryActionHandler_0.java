	/**
	 * @param event
	 * @return the tags
	 * @throws ExecutionException
	 */
	protected List<RevTag> getRevTags(ExecutionEvent event)
			throws ExecutionException {
		Repository repo = getRepository(false, event);
		Collection<Ref> revTags = repo.getTags().values();
		List<RevTag> tags = new ArrayList<RevTag>();
		RevWalk walk = new RevWalk(repo);
		for (Ref ref : revTags) {
			try {
				tags.add(walk.parseTag(repo.resolve(ref.getName())));
			} catch (IOException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
		}
		return tags;
	}
