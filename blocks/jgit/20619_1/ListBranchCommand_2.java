	private Collection<Ref> filterRefs(Collection<Ref> refs)
			throws RefNotFoundException
		if (containsCommitish == null)
			return refs;

		RevWalk walk = new RevWalk(repo);
		try {
			ObjectId resolved = repo.resolve(containsCommitish);
			if (resolved == null)
				throw new RefNotFoundException(MessageFormat.format(
						JGitText.get().refNotResolved

			RevCommit containsCommit = walk.parseCommit(resolved);
			return RevWalkUtils.findBranchesReachableFrom(containsCommit
					refs);
		} finally {
			walk.release();
		}
	}

