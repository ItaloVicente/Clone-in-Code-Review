	public void testImageDataResourceAllocations() throws Exception {
		int[] gResources = { 21, 22, 23 };

		allocateResources(globalResourceManager, gResources);

		Assert.assertEquals(2, TestDescriptor.refCount);
	}

