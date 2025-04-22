			for (RepositoryMapping mapping : getMappings(project).values()) {
				File workTree = mapping.getWorkTree();
				if (workTree == null) {
					continue;
				}
				IPath workingTree = new Path(workTree.toString());
				if (workingTree.isPrefixOf(path)) {
					if (bestWorkingTree == null || workingTree
							.segmentCount() > bestWorkingTree.segmentCount()) {
						bestWorkingTree = workingTree;
						bestMapping = mapping;
					}
