		return compareNames(resource1, resource2);
	}

	public int getCriteria() {
		return criteria;
	}

	private String getExtensionFor(IResource resource) {
		String ext = resource.getFileExtension();
		return ext == null ? "" : ext; //$NON-NLS-1$
	}
