			assertFalse("FormColors disposed different instance's key: " + KEYS_NULL[i],
					nullColors[i] != null && nullColors[i].isDisposed());
		assertFalse("FormColors disposed different instance's getInactiveBackground()", inactiveBg.isDisposed());
		assertFalse("FormColors disposed different instance's getBackground()", bg.isDisposed());
		assertFalse("FormColors disposed different instance's getForeground()", fg.isDisposed());
		assertFalse("FormColors disposed different instance's getBorderColor()", bc.isDisposed());
