	/**
	 * Gets the full unified diff.
	 *
	 * @param tip
	 *            for the diff
	 * @param base
	 *            to compare against, may be {@code null}
	 *
	 * @return the diff as a sorted (by file path) array of {@link FileDiff}s
	 */
	protected FileDiff[] getDiffs(IRepositoryCommit tip,
			IRepositoryCommit base) {
		List<FileDiff> diffResult = new ArrayList<>();

		if (base == null) {
			if (tip instanceof RepositoryCommit) {
				diffResult.addAll(asList(((RepositoryCommit) tip).getDiffs()));
				if (tip.getRevCommit().getParentCount() > 2) {
					RevCommit untrackedCommit = tip.getRevCommit()
							.getParent(StashEditorPage.PARENT_COMMIT_UNTRACKED);
					diffResult.addAll(
							asList(new RepositoryCommit(tip.getRepository(),
									untrackedCommit).getDiffs()));
				}
			}
		} else {
			Repository repository = tip.getRepository();
			RevCommit tipCommit = tip.getRevCommit();
			RevCommit baseCommit = base.getRevCommit();
			FileDiff[] diffsResult = null;
			try (RevWalk revWalk = new RevWalk(repository);
					TreeWalk treewalk = new TreeWalk(
							revWalk.getObjectReader())) {
				treewalk.setRecursive(true);
				treewalk.setFilter(TreeFilter.ANY_DIFF);
				revWalk.parseBody(tipCommit);
				revWalk.parseBody(baseCommit);
				diffsResult = FileDiff.compute(repository, treewalk, tipCommit,
						new RevCommit[] { baseCommit }, null, TreeFilter.ALL);
			} catch (IOException e) {
				diffsResult = new FileDiff[0];
			}
			return diffsResult;
		}

		FileDiff[] result = diffResult.toArray(new FileDiff[0]);
		Arrays.sort(result, FileDiff.PATH_COMPARATOR);
		return result;
	}

