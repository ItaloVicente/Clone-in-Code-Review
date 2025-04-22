	public String[] verifyAndReturnOrder(String[] testNames) throws IllegalArgumentException {
		if (verifyOrder(testNames)) {
			return testNames;
		}
		return methodList.toArray(new String[0]);
	}

