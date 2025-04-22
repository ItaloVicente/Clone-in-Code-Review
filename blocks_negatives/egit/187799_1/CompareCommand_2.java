		List<Ref> refs = new ArrayList<>();
		nodes.forEach(node -> {
			Ref ref = getRef(node);
			if (ref != null) {
				refs.add(ref);
			}
		});
		int numberOfRefs = refs.size();
