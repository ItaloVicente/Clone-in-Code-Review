	@Test
	public void testRemovedPackfileShouldBeDetectedByWindowCursor()
			throws IOException
		BitmapIndex.BitmapBuilder bitmapBuilder = mock(
				BitmapIndex.BitmapBuilder.class);
		doReturn(Boolean.TRUE).when(bitmapBuilder)
				.removeAllOrNone(any(PackBitmapIndex.class));
		WindowCursor wc = new WindowCursor(db.getObjectDatabase());

		createVerifyOpenPack(EMPTY_LIST_REVS);

		simulatePackfileRemoval();
		assertEquals("Removed packfile was not detected by WindowCursor"
				wc.getCachedPacksAndUpdate(bitmapBuilder).size());
	}

