	}

	protected int compareClass(Object element1, Object element2) {
		return classComparison(element1) - classComparison(element2);
	}

	protected int compareNames(IResource resource1, IResource resource2) {
		return getComparator().compare(resource1.getName(), resource2.getName());
	}

	protected int compareTypes(IResource resource1, IResource resource2) {
		String ext1 = getExtensionFor(resource1);
		String ext2 = getExtensionFor(resource2);

		int result = getComparator().compare(ext1, ext2);

		if (result != 0) {
