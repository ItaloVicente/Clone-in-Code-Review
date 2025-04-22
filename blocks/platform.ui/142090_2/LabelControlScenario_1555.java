		assertEquals(adventure.getName(), label.getText());
		adventure.setName("France");
		assertEquals("France", label.getText());
		adventure.setName("Climb Everest");
		spinEventLoop(0);
		assertEquals("Climb Everest", label.getText());
	}
