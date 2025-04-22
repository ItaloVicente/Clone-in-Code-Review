
		if (i > -1 && rules.get(i) != null)
			return rules.get(i).getResult();

		return false;
	}

	/**
	 * @return
	 * 			  True if the previous call to isIgnored resulted in a match,
	 * 			  false otherwise.
	 */
	public boolean wasMatched() {
		return matched;
