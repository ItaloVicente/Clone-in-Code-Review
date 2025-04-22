
	protected List<RefNode> getRefNodes(RevCommit commit, Repository repo, String refPrefix) {
		List<Ref> availableBranches = new ArrayList<Ref>();
		List<RefNode> nodes = new ArrayList<RefNode>();
		try {
			Map<String, Ref> localBranches = repo.getRefDatabase().getRefs(
					refPrefix);
			for (Ref branch : localBranches.values()) {
				if (branch.getLeaf().getObjectId().equals(commit.getId())) {
					availableBranches.add(branch);
				}
			}
			RepositoryNode repoNode = new RepositoryNode(null, repo);
			for (Ref ref : availableBranches) {
				nodes.add(new RefNode(repoNode, repo, ref));
			}
		} catch (IOException e) {
		}
		return nodes;
	}
