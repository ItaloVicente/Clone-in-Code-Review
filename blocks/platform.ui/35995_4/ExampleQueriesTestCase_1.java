
		assertEquals("element1", xpathContext.getValue("nodes[1]/@id"));

		assertEquals(NodeImpl.class,
				xpathContext.getValue("//.[@id='element2.2']").getClass());
		assertEquals(ExtendedNodeImpl.class,
				xpathContext
						.getValue("//.[ecore:eClassName(.)='ExtendedNode']")
						.getClass());

