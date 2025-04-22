		boolean exceptionThrown = false;
		try {
			fActivePage.showView(MockViewPart.ID, "2",
					IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			assertEquals(e.getMessage().indexOf("mult") != -1, true);
			exceptionThrown = true;
		}
		assertEquals(exceptionThrown, true);
