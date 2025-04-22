		for (int i = 0; i < rootItems.length; i++) {
			if (rootItems[i].getText() == null || rootItems[i].getText().equals(""))
				continue;
			if (text && !rootItems[i].getText().startsWith(tlp.getColorName()))
				fail("Wrong text: " + rootItems[i].getText());
			assertEquals(tlp.backgroundColor, rootItems[i].getBackground(0));
			assertEquals(TestLabelProvider.toForegroundColor(tlp.backgroundColor), rootItems[i]
					.getForeground(0));
			assertEquals(tlp.font, rootItems[i].getFont(0));
			assertEquals(tlp.image, rootItems[i].getImage(0));
			if (all)
				checkItems(rootItems[i].getItems(), tlp, all, text);
