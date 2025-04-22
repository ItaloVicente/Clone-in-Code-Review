
	@Test
	public void testTwistieIsVerticallyCentered() {
		createExtendableComposite(shortText,
				ExpandableComposite.LEFT_TEXT_CLIENT_ALIGNMENT | ExpandableComposite.TWISTIE);
		width500();
		update();

		Control[] children = ec.getChildren();

		int textCenter = Geometry.centerPoint(children[1].getBounds()).y;
		int twistieCenter = Geometry.centerPoint(children[0].getBounds()).y;

		assertEquals(textCenter, twistieCenter);
	}
