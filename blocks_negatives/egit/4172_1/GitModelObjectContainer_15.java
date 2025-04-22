	public SaveableComparison getSaveable() {
		return null;
	}

	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
	}

	public String getFullPath() {
		return getLocation().toPortableString();
	}

	public boolean isCompareInputFor(Object object) {
		return false;
	}

