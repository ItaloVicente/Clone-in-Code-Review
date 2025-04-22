		if (expectedResult == null) {
			unknownTests.put(testName, new TestResultFilter(result));
		} else {
			try {
				expectedResult.assertResult(result);
			} catch (Throwable t) {
				errors.put(testName, new TestResultFilter(result));
				throw t;
			}
		}
