	public CreateBranchWizard(Repository repository, String base) {
		try {
			if (base == null) {
				myPage = new CreateBranchPage(repository, (Ref) null);
			} else if (ObjectId.isId(base)) {
				RevCommit commit = new RevWalk(repository).parseCommit(ObjectId
						.fromString(base));
				myPage = new CreateBranchPage(repository, commit);
			} else {
				if (base.startsWith(Constants.R_HEADS)
						|| base.startsWith(Constants.R_REMOTES)
						|| base.startsWith(Constants.R_TAGS)) {
					Ref currentBranch = repository.getRef(base);
					myPage = new CreateBranchPage(repository, currentBranch);
				} else {
					RevCommit commit = new RevWalk(repository)
							.parseCommit(repository.resolve(base + "^{commit}")); //$NON-NLS-1$
					myPage = new CreateBranchPage(repository, commit);
				}
			}
		} catch (IOException e) {
			myPage = new CreateBranchPage(repository, (Ref) null);
		}
