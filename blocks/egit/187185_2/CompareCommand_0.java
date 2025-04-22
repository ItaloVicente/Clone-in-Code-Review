			List<RevCommit> commits = new ArrayList<>();
			Repository repo = nodes.get(0).getRepository();
			int numberOfRefs = 0;
			for (RepositoryTreeNode<?> node : nodes) {
				RevCommit commit = getCommit(repo, node);
				if (commit == null) {
					return null;
				}
				commits.add(commit);
				numberOfRefs++;
			}
