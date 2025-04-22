		Ref ref1 = getRef(nodes.get(0));
		Ref ref2 = getRef(nodes.get(1));
		if (ref1 != null && ref2 != null) {
			Repository repo = nodes.get(0).getRepository();
			IWorkbenchPage workbenchPage = HandlerUtil
					.getActiveWorkbenchWindowChecked(event).getActivePage();
			try {
