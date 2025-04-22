
	@Override
	protected AbstractWorkingSet clone() {
		try {
			AbstractWorkingSet clone = (AbstractWorkingSet) super.clone();
			clone.disconnect();
			return clone;
		} catch (CloneNotSupportedException e) {
		}
		return null;
	}

