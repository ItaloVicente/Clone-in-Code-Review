	@Test
	public void testDetachedWindow() {
		final MWindow window = ems.createModelElement(MWindow.class);
		window.setLabel("MyWindow");
		window.setIconURI("platform:/plugin/org.eclipse.e4.ui.tests/icons/filenav_nav.png");
		final MWindow detachedWindow = ems.createModelElement(MWindow.class);
		detachedWindow.setLabel("DetachedWindow");
		window.getWindows().add(detachedWindow);

		MApplication application = ems.createModelElement(MApplication.class);
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class, application);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		Widget topWidget = (Widget) window.getWidget();
		Widget detachedWidget = (Widget) detachedWindow.getWidget();
		assertNotNull(topWidget);
		assertNotNull(detachedWidget);
		assertEquals(window, ems.getContainer(detachedWindow));
		assertTrue(topWidget instanceof Shell);
		assertTrue(detachedWidget instanceof Shell);
		assertNotNull(((Shell) topWidget).getImage());
		assertNotNull(((Shell) detachedWidget).getImage());
		assertTrue(((Shell) topWidget).getImage() == ((Shell) detachedWidget).getImage());

		application.getChildren().add(detachedWindow);
		while (topWidget.getDisplay().readAndDispatch())
			;
		assertTrue(window.getWindows().isEmpty());
		assertNotEquals(window, ems.getContainer(detachedWindow));
		assertNotNull(((Shell) topWidget).getImage());
		assertNull(((Shell) detachedWidget).getImage());
	}

