			IDecoration decoration) throws IOException {
		String name = node.getObject().getName();
		Ref ref = refCache.findAdditional(node.getRepository(), name);
		if (ref == null) {
			return;
		}
