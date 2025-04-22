	private Map<Ref, Repository> getRefsToDelete(
			List<RepositoryTreeNode> nodes) {
		LinkedHashMap<Ref, Repository> refs = new LinkedHashMap<>();
		for (RepositoryTreeNode node : nodes) {
			if (node instanceof BranchHierarchyNode) {
				try {
					for (Ref ref : ((BranchHierarchyNode) node)
							.getChildRefsRecursive()) {
						refs.put(ref, node.getRepository());
					}
				} catch (IOException e) {
				}
			} else if (node instanceof RefNode) {
				refs.put((Ref) node.getObject(), node.getRepository());
			}
		}
		return refs;
	}

	private Map<Ref, Repository> deleteBranches(final Map<Ref, Repository> refs,
