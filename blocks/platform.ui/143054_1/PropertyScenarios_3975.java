		Text text = new Text(getComposite(), SWT.BORDER);

		getDbc().bindValue(SWTObservables.observeText(text, SWT.FocusOut), BeansObservables.observeValue(adventure, "name"));

		String adventureName = adventure.getName();
		assertEquals(adventureName, text.getText());
		enterText(text, "foobar");
		assertEquals("foobar", adventure.getName());
		adventure.setName("barfoo");
		assertEquals("barfoo", text.getText());
	}

	@Test
