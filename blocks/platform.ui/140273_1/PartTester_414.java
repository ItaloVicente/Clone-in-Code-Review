	private PartTester() {
	}

	public static void testEditor(IEditorPart part) throws Exception {
		testWorkbenchPart(part);

		Assert.isTrue(part.getEditorSite() == part.getSite(),
