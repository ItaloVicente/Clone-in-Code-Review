
	@Override
	public Node appendChild(Node newChild) throws DOMException {
		if (children == null) {
			children = new ArrayList<Node>();
		}
		children.add(newChild);
		return newChild;
	}
