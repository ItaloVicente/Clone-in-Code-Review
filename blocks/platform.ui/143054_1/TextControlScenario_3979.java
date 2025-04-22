		getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(adventure, "name"));

		assertEquals(adventure.getName(), text.getText());
		text.setText("England");
		text.notifyListeners(SWT.FocusOut, null);
		assertEquals("England", adventure.getName());
		adventure.setName("France");
		assertEquals("France", text.getText());
		adventure.setName("Germany");
		spinEventLoop(0);
		assertEquals("Germany", text.getText());
	}

	@Test
