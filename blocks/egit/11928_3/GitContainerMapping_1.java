				result.addAll(createTraversalForContainer(aChild, dataSet));
			else {
				ResourceTraversal traversal = createTraversalForFile(aChild,
						dataSet);
				if (traversal != null)
					result.add(traversal);
			}
