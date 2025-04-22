	public BlameOperation(CommitFileRevision revision, Shell shell,
			IWorkbenchPage page, int lineNumberToReveal) {
		this.fileRevision = revision;
		this.repository = revision.getRepository();
		this.path = revision.getGitPath();
		this.startCommit = revision.getRevCommit();
