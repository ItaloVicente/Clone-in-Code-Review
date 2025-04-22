	public void sortingShouldWorkForEqualAndEmptyStrings() {
		assertEquals(0, CommonUtils.STRING_ASCENDING_COMPARATOR.compare("", ""));
		assertEquals(0,
				CommonUtils.STRING_ASCENDING_COMPARATOR.compare("a", "a"));
		assertTrue(CommonUtils.STRING_ASCENDING_COMPARATOR.compare("", "a") < 0);
		assertTrue(CommonUtils.STRING_ASCENDING_COMPARATOR.compare("a", "") > 0);
	}

