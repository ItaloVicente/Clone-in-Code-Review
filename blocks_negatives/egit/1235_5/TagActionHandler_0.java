	private List<Tag> getRevTags(ExecutionEvent event)
			throws ExecutionException {
		Collection<Ref> revTags = repo.getTags().values();
		List<Tag> tags = new ArrayList<Tag>();
		RevWalk walk = new RevWalk(repo);
		for (Ref ref : revTags) {
			try {
				Tag tag = walk.parseTag(repo.resolve(ref.getName())).asTag(walk);
				tags.add(tag);
			} catch (IOException e) {
				ErrorDialog.openError(getShell(event),
						UIText.TagAction_errorDuringTagging, NLS.bind(
								UIText.TagAction_errorWhileMappingRevTag, ref
										.getName()), new Status(IStatus.ERROR,
								Activator.getPluginId(), e.getMessage(), e));
			}
		}
		return tags;
	}

