		try {
			fViewer.refresh();
			assertEquals(0, list.getTopIndex());
		} catch (Exception e) {
			fail("Refresh failure when refreshing with an empty model.");
		}
