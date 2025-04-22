	private final Git git;
	private final String branchA;
	private final String branchB;

	public ConflictBranchesChecker(final Git git
		this.git = checkNotNull("git"
		this.branchA = checkNotEmpty("branchA"
		this.branchB = checkNotEmpty("branchB"
	}

	public List<String> execute() {
		BranchUtil.existsBranch(this.git

		BranchUtil.existsBranch(this.git

		List<String> result = new ArrayList<>();

		try {
			final RevCommit commitA = git.getLastCommit(branchA);
			final RevCommit commitB = git.getLastCommit(branchB);

			final RevCommit commonAncestor = git.getCommonAncestorCommit(branchA

			ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(git.getRepository()
			merger.setBase(commonAncestor);

			boolean canMerge = merger.merge(commitA

			if (!canMerge) {
				ResolveMerger resolveMerger = (ResolveMerger) merger;
				Map<String
				result.addAll(mergeResults.keySet().stream().sorted(String::compareToIgnoreCase)
						.collect(Collectors.toList()));
			}
		} catch (IOException e) {
			throw new GitException(String.format("Error when checking for conflicts between branches %s and %s: %s"
					this.branchA
		}

		return result;
	}
