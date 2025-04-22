
	private void destinationIntersectsTestCase(String x
			Boolean expected) {
		Boolean xIntersectsY = RefSpec.destinationIntersects(x
		Boolean yIntersectsX = RefSpec.destinationIntersects(y
		assertEquals(xIntersectsY
		assertEquals(yIntersectsX
		assertEquals(xIntersectsY
	}
