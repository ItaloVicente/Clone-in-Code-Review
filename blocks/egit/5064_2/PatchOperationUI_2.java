	private PatchOperationUI(IWorkbenchPart part, Repository repo,
			Collection<? extends IResource> resources) {
		this(part, repo);
		this.resources = resources;
	}

