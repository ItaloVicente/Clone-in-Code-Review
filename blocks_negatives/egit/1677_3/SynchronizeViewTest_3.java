		assertEquals("org.eclipse.egit.core", syncItems[0].getText());
		assertEquals("org.eclipse.egit.ui", syncItems[1].getText());

		syncItems[0].expand();
		syncItems[1].expand();

		assertEquals(1, syncItems[0].getNodes().size());
		assertEquals("pom.xml", syncItems[0].getNodes().get(0));
		assertEquals(1, syncItems[1].getNodes().size());
		assertEquals("pom.xml", syncItems[1].getNodes().get(0));
