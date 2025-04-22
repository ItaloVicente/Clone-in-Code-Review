	@Override
	protected Saveable createSaveable() {
		Object compareResult = getCompareResult();
		Assert.isNotNull(compareResult,
				"This method cannot be called until after prepareInput is called"); //$NON-NLS-1$
		return new InternalResourceSaveableComparison(
				(ICompareInput) compareResult, this);
	}

