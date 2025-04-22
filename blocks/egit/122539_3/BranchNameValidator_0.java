		} catch (RevisionSyntaxException | IOException e1) {
		}
		try {
			List<Ref> branches = Git.wrap(repository.getRepository())
					.branchList().call();
			for (Ref ref : branches) {
				if (fullBranchName.equals(ref.getTarget().getName())) {
					return true;
				}
			}
		} catch (GitAPIException e) {
			throw new CoreException(error(e.getMessage(), e));
