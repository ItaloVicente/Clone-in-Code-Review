	private List<RevTag> getRevTags() {
		Collection<Ref> revTags = repo.getTags().values();
		List<RevTag> tags = new ArrayList<RevTag>();
		RevWalk walk = new RevWalk(repo);
		for (Ref ref : revTags) {
			try {
				tags.add(walk.parseTag(repo.resolve(ref.getName())));
			} catch (IncorrectObjectTypeException e) {
			} catch (IOException e) {
				Activator.logError(
						UIText.TagAction_unableToResolveHeadObjectId, e);
				setErrorMessage(UIText.TagAction_unableToResolveHeadObjectId);
			}
		}
		return tags;
