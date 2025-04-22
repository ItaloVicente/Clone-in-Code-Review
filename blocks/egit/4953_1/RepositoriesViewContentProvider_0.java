	private boolean hasSubmodules(RepositoryTreeNode node, Repository repo) {
		try {
			SubmoduleWalk walk = SubmoduleWalk.forIndex(repo);
			while (walk.next()) {
				Repository subRepo = walk.getRepository();
				if (subRepo != null)
					return true;
			}
		} catch (IOException e) {
			return true;
		}
		return false;
	}

