	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelRepository) {
			File objWorkTree = ((GitModelRepository) obj).repo.getWorkTree();
			return objWorkTree.equals(repo.getWorkTree());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return repo.getWorkTree().hashCode();
	}

	@Override
	public String toString() {
		return "ModelRepository[" + repo.getWorkTree() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

