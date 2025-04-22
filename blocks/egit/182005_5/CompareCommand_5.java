		List<Ref> refs = new ArrayList<>();
		nodes.forEach(node -> {
			Ref ref = getRef(node);
			if (ref != null) {
				refs.add(ref);
			}
		});
		int numberOfRefs = refs.size();
		try {
			if (numberOfRefs == 2) {
				Repository repo = nodes.get(0).getRepository();
				IWorkbenchPage workbenchPage = HandlerUtil
						.getActiveWorkbenchWindowChecked(event).getActivePage();
