		assertNotNull(viewPart);
	}

	public void testPerspectiveReset() {
		IWorkbench workbench = window.getWorkbench();
		IIntroPart part = workbench.getIntroManager().showIntro(window, false);
		assertNotNull(part);
		window.getActivePage().resetPerspective();
		part = workbench.getIntroManager().getIntro();
		assertNotNull(part);
		assertFalse(workbench.getIntroManager().isIntroStandby(part));

		workbench.getIntroManager().setIntroStandby(part, true);
		window.getActivePage().resetPerspective();
		part = workbench.getIntroManager().getIntro();
		assertNotNull(part);
		assertTrue(workbench.getIntroManager().isIntroStandby(part));
		assertTrue(workbench.getIntroManager().closeIntro(part));
		assertNull(workbench.getIntroManager().getIntro());
	}

