		assertTrue(handlers.size() > 0);
		List<MCommand> commands = application.getCommands();
		MCommand expected = commands.get(0);

		long count = handlers.stream().filter(x -> x.getCommand() == expected).count();
		assertEquals(1, count);

		assertEquals(2, expected.getParameters().size());
		assertEquals(1, mWindow2.getVariables().size());

		MWindow mWindow1 = application.getChildren().get(0);
		assertNotNull(mWindow1.getMainMenu());

		List<MWindowElement> children = mWindow1.getChildren();
		assertEquals(8, children.size());
		assertEquals("fragment.contributedPosFirst", children.get(0).getElementId());
		assertEquals("fragment.contributedBeforePart1", children.get(1).getElementId());
		assertEquals("fragment.contributedAfterPart1", children.get(3).getElementId());
		assertEquals("fragment.contributedPos1", children.get(4).getElementId());
		assertEquals("fragment.contributedBeforePart2", children.get(5).getElementId());
		assertEquals("fragment.contributedAfterPart2", children.get(7).getElementId());
