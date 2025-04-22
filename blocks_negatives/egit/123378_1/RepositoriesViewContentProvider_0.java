		case LOCAL: {
			if (branchHierarchyMode) {
				BranchHierarchyNode hierNode = new BranchHierarchyNode(node,
						repo, new Path(Constants.R_HEADS));
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
					for (Ref ref : getRefs(repo, Constants.R_HEADS).values()) {
						if (!ref.isSymbolic())
							refs.add(new RefNode(node, repo, ref));
					}
				} catch (Exception e) {
					return handleException(e, node);
				}
				return refs.toArray();
			}
		}
