	private List<RevTag> getRevTags(Repository repo) throws ExecutionException {
		Collection<Ref> revTags = repo.getTags().values();
		List<RevTag> tags = new ArrayList<RevTag>();
		RevWalk walk = new RevWalk(repo);
		for (Ref ref : revTags) {
			try {
				tags.add(walk.parseTag(repo.resolve(ref.getName())));
			} catch (IOException e) {
				throw new ExecutionException(NLS
						.bind(UIText.TagAction_errorWhileMappingRevTag, ref
								.getName()), e);
			}
		}
		return tags;
	}

	private RevWalk getRevCommits(Repository repo) throws ExecutionException {
		RevWalk revWalk = new RevWalk(repo);
		try {
			revWalk.sort(RevSort.COMMIT_TIME_DESC, true);
			revWalk.sort(RevSort.BOUNDARY, true);
			AnyObjectId headId = repo.resolve(Constants.HEAD);
			if (headId != null)
				revWalk.markStart(revWalk.parseCommit(headId));

		} catch (IOException e) {
			throw new ExecutionException(
					UIText.TagAction_errorWhileGettingRevCommits, e);
		}
		return revWalk;
	}

