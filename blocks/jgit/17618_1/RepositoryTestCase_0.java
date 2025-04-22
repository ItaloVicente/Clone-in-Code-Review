			Repository repo = git.getRepository();
			String originalBranch = repo.getFullBranch();
			boolean empty = repo.resolve(Constants.HEAD) == null;
			if (!empty) {
				if (repo.getRef(branch) == null)
					git.branchCreate().setName(branch).call();
				git.checkout().setName(branch).call();
			}

