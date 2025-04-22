	private final Git git;
	private final RevCommit commitA;
	private final RevCommit commitB;

	public GetCommonAncestorCommit(final Git git
		this.git = checkNotNull("git"
		this.commitA = checkNotNull("commitA"
		this.commitB = checkNotNull("commitB"
	}

	public RevCommit execute() {
		try (final RevWalk revWalk = new RevWalk(git.getRepository())) {
			final RevCommit validatedCommitA = revWalk.lookupCommit(this.commitA);
			final RevCommit validatedCommitB = revWalk.lookupCommit(this.commitB);

			revWalk.setRevFilter(RevFilter.MERGE_BASE);
			revWalk.markStart(validatedCommitA);
			revWalk.markStart(validatedCommitB);
			return revWalk.next();
		} catch (Exception e) {
			throw new GitException("Error when trying to get common ancestor"
		}
	}
