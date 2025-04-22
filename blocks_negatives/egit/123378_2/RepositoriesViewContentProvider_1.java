		case REMOTETRACKING: {
			if (branchHierarchyMode) {
				BranchHierarchyNode hierNode = new BranchHierarchyNode(node,
						repo, new Path(Constants.R_REMOTES));
				List<RepositoryTreeNode> children = new ArrayList<>();
				try {
					for (IPath path : hierNode.getChildPaths()) {
						children.add(new BranchHierarchyNode(node, node
								.getRepository(), path));
					}
					for (Ref ref : hierNode.getChildRefs()) {
						children.add(new RefNode(node, node.getRepository(),
								ref));
					}
				} catch (Exception e) {
					return handleException(e, node);
				}
				return children.toArray();
			} else {
				List<RepositoryTreeNode<Ref>> refs = new ArrayList<>();
				try {
					for (Entry<String, Ref> refEntry : getRefs(repo, Constants.R_REMOTES).entrySet()) {
						if (!refEntry.getValue().isSymbolic())
							refs.add(new RefNode(node, repo, refEntry
									.getValue()));
					}
				} catch (Exception e) {
					return handleException(e, node);
				}

				return refs.toArray();
			}
		}
