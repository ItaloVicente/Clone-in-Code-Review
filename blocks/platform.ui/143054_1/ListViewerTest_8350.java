		try {
			Device.DEBUG = true;
			int topIndex = list.getTopIndex();
			assertNotEquals("Top item should not be the first item.", 0, topIndex);
			fViewer.refresh();
			processEvents();
			assertEquals("Top index was not preserved after refresh.", topIndex, list.getTopIndex());
