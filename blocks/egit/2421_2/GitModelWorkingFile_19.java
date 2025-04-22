	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelWorkingFile)
			return ((GitModelWorkingFile) obj).getLocation()
					.equals(getLocation());

		return false;
	}

	@Override
	public int hashCode() {
		return getLocation().hashCode();
	}

	@Override
	public String toString() {
		return "ModelWorkingFile[" + getLocation() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

