			assertFalse("FormToolkit disposed shared FormColor's key: " + KEYS_NULL[i],
					nullColors[i] != null && nullColors[i].isDisposed());
		assertFalse("FormToolkit disposed shared FormColor's getInactiveBackground()", inactiveBg.isDisposed());
		assertFalse("FormToolkit disposed shared FormColor's getBackground()", bg.isDisposed());
		assertFalse("FormToolkit disposed shared FormColor's getForeground()", fg.isDisposed());
		assertFalse("FormToolkit disposed shared FormColor's getBorderColor()", bc.isDisposed());
