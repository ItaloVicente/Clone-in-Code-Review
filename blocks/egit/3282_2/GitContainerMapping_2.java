	private List<ResourceTraversal> createTraversalForContainer(GitModelObject child) {
		GitModelObject[] containerChildren = child.getChildren();
		List<ResourceTraversal> result = new ArrayList<ResourceTraversal>();
		for (GitModelObject aChild : containerChildren) {
			if(aChild.isContainer()) {
				result.addAll(createTraversalForContainer(aChild));
