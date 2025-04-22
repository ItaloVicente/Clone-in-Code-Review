
		MWindow mWindow = application.getChildren().get(0);
		assertNotNull(mWindow.getMainMenu());

		List<MWindowElement> children = mWindow.getChildren();
		assertEquals(8, children.size());
		assertEquals("fragment.contributedPosFirst", children.get(0).getElementId());
		assertEquals("fragment.contributedBeforePart1", children.get(1).getElementId());
		assertEquals("fragment.contributedAfterPart1", children.get(3).getElementId());
		assertEquals("fragment.contributedPos1", children.get(4).getElementId());
		assertEquals("fragment.contributedBeforePart2", children.get(5).getElementId());
		assertEquals("fragment.contributedAfterPart2", children.get(7).getElementId());
