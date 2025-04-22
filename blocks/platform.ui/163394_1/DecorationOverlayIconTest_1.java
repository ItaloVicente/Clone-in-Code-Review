	public void testEqualsAndHashCodeDoneMatch() {

		assertTrue(equalButDifferent1.equals(equalButDifferent2));
		assertEquals(equalButDifferent1.hashCode(), equalButDifferent2.hashCode());

		DecorationOverlayIcon equalButDifferentIcon1 = new DecorationOverlayIcon(equalButDifferent1, overlayDescriptor1,
				IDecoration.TOP_LEFT);
		DecorationOverlayIcon equalButDifferentIcon2 = new DecorationOverlayIcon(equalButDifferent2, overlayDescriptor1,
				IDecoration.TOP_LEFT);
		assertTrue(equalButDifferentIcon1.equals(equalButDifferentIcon2));
		assertEquals(equalButDifferentIcon1.hashCode(), equalButDifferentIcon2.hashCode());
	}

