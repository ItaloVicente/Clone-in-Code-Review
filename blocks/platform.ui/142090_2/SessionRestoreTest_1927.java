		pages = windows[2].getPages();
		assertEquals(pages.length, 2);
		assertEquals(pages[0].getPerspective().getId(), SessionPerspective.ID);
		assertEquals(pages[1].getPerspective().getId(), SessionPerspective.ID);
		testSessionView(pages[0]);
		testSessionView(pages[1]);
