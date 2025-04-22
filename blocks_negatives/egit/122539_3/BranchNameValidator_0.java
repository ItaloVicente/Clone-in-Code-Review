			branches = Git.wrap(repository.getRepository()).branchList().call();
		} catch (GitAPIException e) {
			throw new CoreException(error(e.getMessage(), e));
		}
		for (Ref ref : branches) {
			if (fullBranchName.equals(ref.getTarget().getName())) {
