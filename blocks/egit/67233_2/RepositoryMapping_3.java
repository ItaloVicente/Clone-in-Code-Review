			Map<IPath, RepositoryMapping> mappings = getMappings(project);
			for (RepositoryMapping mapping : mappings.values()) {
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
