	public void testShow() {
		IIntroManager introManager = window.getWorkbench().getIntroManager();
		IIntroPart part = introManager.showIntro(window, false);
		assertNotNull(part);
		assertFalse(introManager.isIntroStandby(part));
		introManager.closeIntro(part);
		assertNull(introManager.getIntro());

		part = introManager.showIntro(window, true);
		assertNotNull(part);
		assertTrue(introManager.isIntroStandby(part));
		assertTrue(introManager.closeIntro(part));
		assertNull(introManager.getIntro());
	}
