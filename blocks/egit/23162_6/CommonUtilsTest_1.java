
		assertEquals(0,
				CommonUtils.STRING_ASCENDING_COMPARATOR.compare("01", "1"));
		assertEquals(0,
				CommonUtils.STRING_ASCENDING_COMPARATOR.compare("1", "01"));
		assertTrue(CommonUtils.STRING_ASCENDING_COMPARATOR.compare("01x", "1") > 0);
		assertTrue(CommonUtils.STRING_ASCENDING_COMPARATOR.compare("01", "1x") < 0);
