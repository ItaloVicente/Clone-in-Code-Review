		Node parent = elt.getParentNode();

		Node[] hierarchy;
		if (parent == null) {
			hierarchy = new Node[] { null };
		} else {
			List<Node> hierarchyList = new ArrayList<>();
			for (Node n = elt.getParentNode();; n = n.getParentNode()) {
				hierarchyList.add(n);
				if (n == null) {
					break;
				}
			}
			hierarchy = hierarchyList.toArray(new Node[hierarchyList.size()]);
		}

