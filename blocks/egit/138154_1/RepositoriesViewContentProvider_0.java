			LocalNode localBranches = new LocalNode(node, repo);
			if (showRemoteBranches.get()) {
				List<RepositoryTreeNode> nodes = new ArrayList<>();
				nodes.add(localBranches);
				nodes.add(new RemoteTrackingNode(node, repo));
				return nodes.toArray();
			} else {
				return getBranchChildren(localBranches, repo,
						Constants.R_HEADS);
			}
