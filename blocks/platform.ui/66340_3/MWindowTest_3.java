	@Test
	public void testDetachedWindow() {
		final MWindow window = ems.createModelElement(MWindow.class);
		window.setLabel("MyWindow");
		final MWindow detachedWindow = ems.createModelElement(MWindow.class);
		detachedWindow.setLabel("DetachedWindow");
		window.getWindows().add(detachedWindow);

		MApplication application = ems.createModelElement(MApplication.class);
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class, application);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		assertTrue(window.getWidget() instanceof Shell);
		assertTrue(detachedWindow.getWidget() instanceof Shell);
		Shell topShell = (Shell) window.getWidget();
		Shell detachedShell = (Shell) detachedWindow.getWidget();
		assertEquals(window, ems.getContainer(detachedWindow));
		assertNull("Should have no shell image", topShell.getImage());
		assertEquals("Detached should have same image", topShell.getImage(), detachedShell.getImage());

		window.setIconURI("platform:/plugin/org.eclipse.e4.ui.tests/icons/filenav_nav.png");
		while (topShell.getDisplay().readAndDispatch()) {
		}
		assertNotNull("Should have shell image", topShell.getImage());
		assertEquals("Detached should have same image", topShell.getImage(), detachedShell.getImage());

		window.setIconURI(null);
		while (topShell.getDisplay().readAndDispatch()) {
		}
		assertNull("Should have no shell image", topShell.getImage());
		assertEquals("Detached should have same image", topShell.getImage(), detachedShell.getImage());

		window.setIconURI("platform:/plugin/org.eclipse.e4.ui.tests/icons/filenav_nav.png");
		application.getChildren().add(detachedWindow);
		while (topShell.getDisplay().readAndDispatch()) {
		}
		assertTrue(window.getWindows().isEmpty());
		assertNotEquals(window, ems.getContainer(detachedWindow));
		assertNotNull(topShell.getImage());
		assertNull(detachedShell.getImage());
	}

