		Display disp = PlatformUI.createDisplay();
		assertNotNull(disp);
		assertFalse(disp.isDisposed());
		disp.dispose();
		assertTrue(disp.isDisposed());
	}
