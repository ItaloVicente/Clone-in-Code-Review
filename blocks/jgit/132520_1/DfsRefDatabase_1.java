	public boolean hasRefs(Map<String
		for (String refName : expectations.keySet()) {
			if (exactRef(refName) == null) {
				return false;
			}
		}
		return true;
	}

