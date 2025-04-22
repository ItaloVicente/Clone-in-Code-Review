			if (workingTree.isPrefixOf(path)) {
				if (bestWorkingTree == null
						|| workingTree.segmentCount() > bestWorkingTree
								.segmentCount()) {
					bestWorkingTree = workingTree;
					bestMapping = mapping;
				}
			}
