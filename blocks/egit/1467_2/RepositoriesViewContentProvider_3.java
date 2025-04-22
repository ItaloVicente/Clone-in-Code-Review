			if (branchHierarchyMode) {
				BranchHierarchyNode hierNode = new BranchHierarchyNode(node,
						repo, new Path(Constants.R_REMOTES));
				List<RepositoryTreeNode> children = new ArrayList<RepositoryTreeNode>();
				try {
					for (IPath path : hierNode.getChildPaths()) {
						children.add(new BranchHierarchyNode(node, node
								.getRepository(), path));
					}
					for (Ref ref : hierNode.getChildRefs()) {
						children.add(new RefNode(node, node.getRepository(),
								ref));
					}
				} catch (IOException e) {
					return handleException(e, node);
				}
				return children.toArray();
			} else {
				List<RepositoryTreeNode<Ref>> refs = new ArrayList<RepositoryTreeNode<Ref>>();
				try {
					for (Entry<String, Ref> refEntry : repo.getRefDatabase()
							.getRefs(Constants.R_REMOTES).entrySet()) {
						if (!refEntry.getValue().isSymbolic())
							refs.add(new RefNode(node, repo, refEntry
									.getValue()));
					}
				} catch (IOException e) {
					return handleException(e, node);
				}
