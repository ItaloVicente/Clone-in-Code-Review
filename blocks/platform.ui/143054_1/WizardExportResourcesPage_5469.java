		return true;
	}

	protected List extractNonLocalResources(List originalList) {
		ArrayList result = new ArrayList(originalList.size());
		Iterator resourcesEnum = originalList.iterator();

		while (resourcesEnum.hasNext()) {
			IResource currentResource = (IResource) resourcesEnum.next();
			if (!currentResource.isLocal(IResource.DEPTH_ZERO)) {
				result.add(currentResource);
