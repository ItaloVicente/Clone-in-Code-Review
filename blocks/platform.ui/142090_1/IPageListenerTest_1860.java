		fWindow.removePageListener(this);
		super.doTearDown();
	}

	public void testPageOpened() throws Throwable {

		 eventsReceived = 0;
		 IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);
		 assertEquals(eventsReceived, OPEN|ACTIVATE);

		 page.close();
	}

	public void testPageClosed() throws Throwable {

		 IWorkbenchPage page = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);

		 eventsReceived = 0;
		 pageMask = page;
		 page.close();
		 assertEquals(eventsReceived, CLOSE);
	}

	public void testPageActivate() throws Throwable {

		 IWorkbenchPage page1 = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);
		 IWorkbenchPage page2 = fWindow.openPage(EmptyPerspective.PERSP_ID,
		 fWorkspace);

		 eventsReceived = 0;
		 pageMask = page1;
		 fWindow.setActivePage(page1);
		 assertEquals(eventsReceived, ACTIVATE);

		 eventsReceived = 0;
		 pageMask = page2;
		 fWindow.setActivePage(page2);
		 assertEquals(eventsReceived, ACTIVATE);

		 page1.close();
		 page2.close();
	}

	@Override
