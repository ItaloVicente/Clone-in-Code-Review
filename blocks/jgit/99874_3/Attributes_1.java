	public boolean canBeContentMerged() {
		if (isUnset(Constants.ATTR_MERGE)) {
			return false;
		} else if (isCustom(Constants.ATTR_MERGE)
				&& getValue(Constants.ATTR_MERGE)
						.equals(Constants.ATTR_BUILTIN_BINARY_MERGER)) {
			return false;
		}
		return true;
	}

