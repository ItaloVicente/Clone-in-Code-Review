	@Ignore
	@Test
	public void testFindPerspectiveWithLabel() {
		IPerspectiveDescriptor pers = (IPerspectiveDescriptor) ArrayUtil.pickRandom(fReg.getPerspectives());

		IPerspectiveDescriptor suspect = fReg.findPerspectiveWithLabel(pers.getLabel());
		assertNotNull(suspect);
		assertEquals(pers, suspect);

		suspect = fReg.findPerspectiveWithLabel(IConstants.FakeLabel);
		assertNull(suspect);
	}

