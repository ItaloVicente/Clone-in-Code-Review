	public boolean isBinary() {
		if (isUnset(Constants.ATTR_MERGE)) {
				return true;
		} else if (isCustom(Constants.ATTR_MERGE)
				&& getValue(Constants.ATTR_MERGE)
						.equals(Constants.ATTR_BUILTIN_BINARY_MERGER)) {
			return true;
		}
		return false;
	}

