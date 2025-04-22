	private Node[] getHierarchy(Node node) {
		if (node == null) {
			return null;
		}
		return nodeHierarchyCache.computeIfAbsent(node, parent -> {
			List<Node> hierarchyList = new ArrayList<>();
			for (Node n = parent; n != null; n = n.getParentNode()) {
				hierarchyList.add(n);
			}
			return hierarchyList.toArray(new Node[hierarchyList.size()]);
		});
	}

