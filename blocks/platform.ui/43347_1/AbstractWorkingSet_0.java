
	protected AbstractWorkingSet safeClone() {
		try {
			return (AbstractWorkingSet) super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}

