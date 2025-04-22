
	@Override
	public void swapIndex(IWorkingSet one, IWorkingSet other) {
		int indexOne = this.workingSets.indexOf(one);
		int indexOther = this.workingSets.indexOf(other);
		if (indexOne < 0) {
			throw new IllegalArgumentException("Working set " + one.getId() + " doesn't exist"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (indexOther < 0) {
			throw new IllegalArgumentException("Working set " + other.getId() + " doesn't exist"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		this.workingSets.set(indexOne, other);
		this.workingSets.set(indexOther, one);
	}
