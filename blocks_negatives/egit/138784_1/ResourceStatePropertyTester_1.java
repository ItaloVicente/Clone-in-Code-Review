		boolean result = internalTest(receiver, prop);
		if (expectedValue != null) {
			return expectedValue.equals(Boolean.valueOf(result));
		} else {
			return result;
		}
