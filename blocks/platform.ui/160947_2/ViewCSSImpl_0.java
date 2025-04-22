		Node parent = elt.getParentNode();

		Node[] hierarchy = null;
		if (parent != null) {
			List<Node> hierarchyList = new ArrayList<>();
			for (Node n = elt.getParentNode(); n != null; n = n.getParentNode()) {
				hierarchyList.add(n);
			}
			hierarchy = hierarchyList.toArray(new Node[hierarchyList.size()]);
		}

