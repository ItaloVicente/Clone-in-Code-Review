
		if ("isSubmodule".equals(property)) { //$NON-NLS-1$
			RepositoryTreeNode<?> parent = node.getParent();
			return parent != null
					&& parent.getType() == RepositoryTreeNodeType.SUBMODULES;
		}
