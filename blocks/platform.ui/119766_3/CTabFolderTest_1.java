
	@Test
	public void testStyleLabelChildInCTabFolder() {
		Label labelToTest = createLabelInCTabFolder("Label { background-color: #0000FF; }\n");
		assertEquals(BLUE, labelToTest.getBackground().getRGB());
	}
