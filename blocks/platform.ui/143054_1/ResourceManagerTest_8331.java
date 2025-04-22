		super.tearDown();
		globalResourceManager.dispose();
		Assert.assertEquals("Detected leaks", 0, TestDescriptor.refCount);
		testImage.dispose();
		testImage2.dispose();
	}

	protected void validateResource(Object resource) throws Exception {
		Assert.assertNotNull("Allocated resource was null", resource);
		if (resource instanceof Image) {
			Image image = (Image) resource;

			Assert.assertTrue("Image is disposed", !image.isDisposed());
			return;
		}
	}

	public void testDescriptorAllocations() throws Exception {
		Display display = Display.getCurrent();

		Object[] resources = new Object[descriptors.length];

		for (int i = 0; i < descriptors.length; i++) {
			DeviceResourceDescriptor next = descriptors[i];

			Object resource = next.createResource(display);
			validateResource(resource);
			resources[i] = resource;
		}


		Assert.assertEquals("Expecting one resource to be allocated per descriptor",
				descriptors.length, TestDescriptor.refCount);

		for (int i = 0; i < descriptors.length; i++) {
			DeviceResourceDescriptor next = descriptors[i];

			next.destroyResource(resources[i]);
		}
	}

	public void testDeviceManagerAllocations() throws Exception {

		Object[] resources = new Object[descriptors.length];

		for (int i = 0; i < descriptors.length; i++) {
			DeviceResourceDescriptor next = descriptors[i];

			Object resource = globalResourceManager.create(next);
			validateResource(resource);
			resources[i] = resource;
		}

		Assert.assertEquals("Descriptors created from Images should not reallocate Images when the original can be reused",
				testImage, resources[9]);


		Assert.assertEquals("Duplicate descriptors should be reused",
				descriptors.length - numDupes,
				TestDescriptor.refCount);

		for (DeviceResourceDescriptor next : descriptors) {
			globalResourceManager.destroy(next);
		}
	}

	private void allocateResources(ResourceManager mgr, int[] toAllocate) throws Exception {
		for (int j : toAllocate) {
			validateResource(mgr.create(descriptors[j]));
		}
	}

	private void deallocateResources(ResourceManager mgr, int[] toDeallocate) {
		for (int j : toDeallocate) {
			mgr.destroy(descriptors[j]);
		}
	}

	public void testLocalManagerAllocations() throws Exception {
		int[] gResources = {0, 1, 5, 3, 7, 12, 13, 14};
		int[] lm1Resources = {0, 2, 3, 4, 11, 12, 13, 15};
		int[] lm2Resources = {0, 1, 6, 7, 8, 12, 14, 16};

		LocalResourceManager lm1 = new LocalResourceManager(globalResourceManager);
		LocalResourceManager lm2 = new LocalResourceManager(globalResourceManager);

		allocateResources(globalResourceManager, gResources);

		int initialCount = TestDescriptor.refCount;

		allocateResources(lm1, lm1Resources);

		int lm1Count = TestDescriptor.refCount;

		allocateResources(lm2, lm2Resources);
		deallocateResources(lm2, lm2Resources);
		Assert.assertEquals(lm1Count, TestDescriptor.refCount);

		lm2.dispose();
		Assert.assertEquals(lm1Count, TestDescriptor.refCount);

		lm1.dispose();
		Assert.assertEquals(initialCount, TestDescriptor.refCount);

	}

	public void testResourceManagerFind() throws Exception {
		DeviceResourceDescriptor descriptor = descriptors[0];
		Object resource = globalResourceManager.find(descriptor);
		assertNull("Resource should be null since it is not allocated", resource);
		resource = globalResourceManager.create(descriptor);

		validateResource(resource);

		Object foundResource = globalResourceManager.find(descriptor);
		assertNotNull("Found resource should not be null", foundResource);
		assertTrue("Found resource should be an image", foundResource instanceof Image);

		globalResourceManager.destroy(descriptor);
	}
