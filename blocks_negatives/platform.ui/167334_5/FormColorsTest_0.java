		assertTrue("FormColors did not dispose key: test", testColor.isDisposed());
		assertTrue("FormColors did not dispose getInactiveBackground()", inactiveBg.isDisposed());
		assertFalse("FormColors disposed getBackground()", bg.isDisposed());
		assertFalse("FormColors disposed getForeground()", fg.isDisposed());
		if (testBorderDispose)
			assertFalse("FormColors disposed getBorderColor() when it shouldn't have", bc.isDisposed());
