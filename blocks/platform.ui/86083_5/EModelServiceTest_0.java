	@Test
	public void testCreatePartFromDescriptorWithTrimBars() {
		MPartDescriptor mPartDescriptor = ems.createModelElement(MPartDescriptor.class);
		MTrimBar mTrimBar = ems.createModelElement(MTrimBar.class);
		mTrimBar.setElementId("test.trimbar.id");
		mPartDescriptor.getTrimBars().add(mTrimBar);

		MPart newPart = ems.createPart(mPartDescriptor);

		assertEquals(1, newPart.getTrimBars().size());
		assertEquals(1, mPartDescriptor.getTrimBars().size());
		assertEquals(newPart.getTrimBars().get(0).getElementId(), mPartDescriptor.getTrimBars().get(0).getElementId());

	}
