	private int totalFixed(MGenericTile<?> node) {
		int total = 0;
		for (MUIElement subNode : node.getChildren()) {
			if (subNode.isToBeRendered() && subNode.isVisible())
				total += getFixed(subNode);
		}
		return total;
	}

