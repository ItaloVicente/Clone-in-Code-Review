				return refs.toArray();
			}
		}

		case BRANCHHIERARCHY: {
			BranchHierarchyNode hierNode = (BranchHierarchyNode) node;
			List<RepositoryTreeNode> children = new ArrayList<RepositoryTreeNode>();
