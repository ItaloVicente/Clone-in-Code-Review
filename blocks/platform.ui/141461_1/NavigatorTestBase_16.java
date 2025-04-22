		assertEquals(tlp.backgroundColor, rootItem.getBackground(0));
		assertEquals(TestLabelProvider.toForegroundColor(tlp.backgroundColor), rootItem.getForeground(0));
		assertEquals(tlp.font, rootItem.getFont(0));
		assertEquals(tlp.image, rootItem.getImage(0));
		if (all) {
		    checkItems(rootItem.getItems(), tlp, all, text);
		}
	    }
