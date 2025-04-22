		assertNotEquals("Top item should not be the first item.", 0, topIndex);
		fViewer.refresh();
		processEvents();
		assertEquals("Top index was not preserved after refresh.", topIndex, list.getTopIndex());

		model.deleteChildren();

		fViewer.refresh();
		assertEquals(0, list.getTopIndex());
