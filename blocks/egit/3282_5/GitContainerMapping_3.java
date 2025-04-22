			if (child.isContainer())
				result.addAll(createTraversalForContainer(child));
			else
				result.add(createTraversalForFile(child));
		}
		result.removeAll(Collections.singleton(null));
		return result.toArray(new ResourceTraversal[result.size()]);
	}
