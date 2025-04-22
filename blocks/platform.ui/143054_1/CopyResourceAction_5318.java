		if (selectedResources.isEmpty()) {
			return false;
		}
		IContainer firstParent = ((IResource) selectedResources.get(0))
				.getParent();
		if (firstParent == null) {
