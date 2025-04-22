
		if (traversals.length == 0)
			return Collections.emptyList();

		List<IResource> result = new ArrayList<IResource>();
		for (ResourceTraversal traversal : traversals) {
			IResource[] resources = traversal.getResources();
			result.addAll(Arrays.asList(resources));
		}
		return result;
