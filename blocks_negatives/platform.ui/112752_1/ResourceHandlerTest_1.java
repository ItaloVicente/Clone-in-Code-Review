		assertNotNull(application.getChildren().get(0).getMainMenu());
		assertEquals(8, application.getChildren().get(0).getChildren().size());
		assertEquals("fragment.contributedPosFirst", application.getChildren()
				.get(0).getChildren().get(0).getElementId());
		assertEquals("fragment.contributedBeforePart1", application
				.getChildren().get(0).getChildren().get(1).getElementId());
		assertEquals("fragment.contributedAfterPart1", application
				.getChildren().get(0).getChildren().get(3).getElementId());
		assertEquals("fragment.contributedPos1", application.getChildren().get(0).getChildren().get(4).getElementId());
		assertEquals("fragment.contributedBeforePart2", application
				.getChildren().get(0).getChildren().get(5).getElementId());
		assertEquals("fragment.contributedAfterPart2", application
				.getChildren().get(0).getChildren().get(7).getElementId());
