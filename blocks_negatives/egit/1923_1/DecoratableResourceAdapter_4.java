		IProject project = resource.getProject();
		IWorkspaceRoot workspaceRoot = resource.getWorkspace().getRoot();
		File repoRoot = repository.getWorkTree();

		if (project != null && project.getLocation() != null
				&& repoRoot.equals(project.getLocation().toFile()))
			treeWalk.addTree(new ContainerTreeIterator(project));
		else if (repoRoot.equals(workspaceRoot.getLocation().toFile()))
			treeWalk.addTree(new ContainerTreeIterator(workspaceRoot));
		else
			treeWalk.addTree(new AdaptableFileTreeIterator(repoRoot,
					workspaceRoot));

