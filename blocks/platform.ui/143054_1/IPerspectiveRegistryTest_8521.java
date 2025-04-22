		fReg = PlatformUI.getWorkbench().getPerspectiveRegistry();
	}

	public void testFindPerspectiveWithId() {
		IPerspectiveDescriptor pers = (IPerspectiveDescriptor) ArrayUtil
				.pickRandom(fReg.getPerspectives());

		IPerspectiveDescriptor suspect = fReg.findPerspectiveWithId(pers
				.getId());
		assertNotNull(suspect);
		assertEquals(pers, suspect);

		suspect = fReg.findPerspectiveWithId(IConstants.FakeID);
		assertNull(suspect);
	}

	 public void testFindPerspectiveWithLabel()
	 {
	 IPerspectiveDescriptor pers = ( IPerspectiveDescriptor )ArrayUtil.pickRandom( fReg.getPerspectives() );

	 IPerspectiveDescriptor suspect = fReg.findPerspectiveWithLabel( pers.getLabel() );
	 assertNotNull( suspect );
	 assertEquals( pers, suspect );

	 suspect = fReg.findPerspectiveWithLabel( IConstants.FakeLabel );
	 assertNull( suspect );
	 }
	public void testGetDefaultPerspective() {
		String id = fReg.getDefaultPerspective();
		assertNotNull(id);

		IPerspectiveDescriptor suspect = fReg.findPerspectiveWithId(id);
		assertNotNull(suspect);
	}

	public void testSetDefaultPerspective() {
		IPerspectiveDescriptor pers = (IPerspectiveDescriptor) ArrayUtil
				.pickRandom(fReg.getPerspectives());
		fReg.setDefaultPerspective(pers.getId());

		assertEquals(pers.getId(), fReg.getDefaultPerspective());
	}

	public void testGetPerspectives() throws Throwable {
		IPerspectiveDescriptor[] pers = fReg.getPerspectives();
		assertNotNull(pers);

		for (IPerspectiveDescriptor per : pers) {
