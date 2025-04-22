				if (base.startsWith(Constants.R_HEADS)
						|| base.startsWith(Constants.R_REMOTES)
						|| base.startsWith(Constants.R_TAGS)) {
					Ref currentBranch = repository.getRef(base);
					myPage = new CreateBranchPage(repository, currentBranch);
				} else {
					RevCommit commit = new RevWalk(repository)
					myPage = new CreateBranchPage(repository, commit);
				}
