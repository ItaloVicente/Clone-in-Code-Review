		IStructuredSelection selection = getSelection(getPage());
		if (selection.size() == 1) {
			RevCommit commit = (RevCommit) selection.getFirstElement();
			Repository repo = getRepository(event);
			PatchOperationUI.createPatch(getPart(event), commit, repo).start();
		}
