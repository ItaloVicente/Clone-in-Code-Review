		return getPart(event).getSite().getPage();
	}

	protected IWorkbenchPart getPart(ExecutionEvent event)
			throws ExecutionException {
		return HandlerUtil.getActivePartChecked(event);
	}

	protected List<Tag> getRevTags(ExecutionEvent event)
			throws ExecutionException {
		Repository repo = getRepository(false, event);
		Collection<Ref> revTags = repo.getTags().values();
		List<Tag> tags = new ArrayList<Tag>();
		RevWalk walk = new RevWalk(repo);
		for (Ref ref : revTags) {
			try {
				Tag tag = walk.parseTag(repo.resolve(ref.getName()))
						.asTag(walk);
				tags.add(tag);
			} catch (IOException e) {
				throw new ExecutionException(e.getMessage(), e);
			}
		}
		return tags;
