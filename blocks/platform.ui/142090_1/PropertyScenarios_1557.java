		Text text = new Text(getComposite(), SWT.BORDER);
		getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(adventure, "name"));

		assertEquals(adventure.getName(), text.getText());
		enterText(text, "foobar");
		assertEquals("foobar", adventure.getName());
		adventure.setName("barfoo");
		assertEquals("barfoo", text.getText());
	}

	@Test
