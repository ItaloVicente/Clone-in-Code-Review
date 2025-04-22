		assertEquals(
				"Only making a part visible, the active part should not have changed",
				partA, partService.getActivePart());
		assertNotNull("The shown part should have a context",
				partB.getContext());
		assertTrue(
				"The part is the only one in the stack, it should be visible",
				partService.isPartVisible(partB));
