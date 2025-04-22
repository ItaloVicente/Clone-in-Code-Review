			String testName = testNames[testIndex];
			testMethodName(testName);
			if (testName.equals(methodName))
				++testIndex;
			if (testIndex >= testLength)
				return true;
		}
		return false;
	}
