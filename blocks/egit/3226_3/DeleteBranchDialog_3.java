
	private List<RefNode> extractSelectedRefNodes() {
		List<RefNode> nodes = new ArrayList<RefNode>();
		Object[] array = ((IStructuredSelection) super.branchTree
				.getSelection()).toArray();

		for (Object selected : array)
			if (selected instanceof RefNode)
				nodes.add((RefNode) selected);

		return nodes;
	}

