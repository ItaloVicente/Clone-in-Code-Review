			if (node instanceof BranchHierarchyNode) {
				try {
					for (Ref ref : ((BranchHierarchyNode) node)
							.getChildRefsRecursive()) {
						if (isRefCheckedOut(repository, ref)) {
							return true;
						}
