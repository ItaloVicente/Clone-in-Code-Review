	private void assertAllChildrenHaveWidth(int desiredWidth) {
		Control[] children = inner.getChildren();

		for (int idx = 0; idx < children.length; idx++) {
			Control next = children[idx];

			Rectangle bounds = next.getBounds();
			assertEquals("Child " + idx + " should have the correct width", desiredWidth, bounds.width);
		}
	}
