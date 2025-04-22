		for (IProject project : gsd.getProjects()) {
			treeEntry = tree.findTreeMember(project.getName());

			if (treeEntry != null && treeEntry instanceof Tree) {
				projTree = (Tree) treeEntry;
				parent = new GitProject(project, projTree);
			} else {
				projTree = tree;
				parent = new GitProject(project, projTree);
