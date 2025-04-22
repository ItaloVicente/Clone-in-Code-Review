	@Override
	protected boolean setVariant(IResource local, IResourceVariant remote)
			throws TeamException {
		return true;
	}

	protected GitSynchronizeDataSet getSyncData() {
		return gsdData;
	}
