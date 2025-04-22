	@Override
	protected Supplier<String> internalGetAttribute(String attr) {
		if ("swt-lines-visible".equals(attr)) {
			Tree tree = getTree();
			return () -> String.valueOf(tree.getLinesVisible());
		}
		return super.internalGetAttribute(attr);
	}
