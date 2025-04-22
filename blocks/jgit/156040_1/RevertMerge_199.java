	private final Git git;
	private final String sourceBranch;
	private final String targetBranch;
	private final String commonAncestorCommitId;
	private final String mergeCommitId;

	public RevertMerge(final Git git
			final String commonAncestorCommitId
		this.git = checkNotNull("git"
		this.sourceBranch = checkNotEmpty("sourceBranch"
		this.targetBranch = checkNotEmpty("targetBranch"
		this.commonAncestorCommitId = checkNotEmpty("commonAncestorCommitId"
		this.mergeCommitId = checkNotEmpty("mergeCommitId"
	}

	public boolean execute() {
		BranchUtil.existsBranch(git
		BranchUtil.existsBranch(git

		final RevCommit lastSourceCommit = git.getLastCommit(sourceBranch);
		final RevCommit lastTargetCommit = git.getLastCommit(targetBranch);

		boolean isDone = false;

		if (canRevert(lastSourceCommit

			git.commit(targetBranch
					lastTargetCommit.getParent(0)

			final RevCommit newLastTargetCommit = git.getLastCommit(targetBranch);

			final List<RevCommit> parents = Stream.of(lastSourceCommit
					.collect(Collectors.toList());

			final Map<String
					newLastTargetCommit.getName());

			git.commit(sourceBranch
					new MergeCommitContent(contents

			git.commit(sourceBranch
					git.getLastCommit(sourceBranch).getParent(0)

			isDone = true;
		}

		return isDone;
	}

	private boolean canRevert(final RevCommit lastSourceCommit
		return lastTargetCommit.getParentCount() > 1 && lastTargetCommit.getName().equals(mergeCommitId)
				&& lastTargetCommit.getParent(0).getName().equals(commonAncestorCommitId)
				&& lastTargetCommit.getParent(1).getName().equals(lastSourceCommit.getName());
	}
