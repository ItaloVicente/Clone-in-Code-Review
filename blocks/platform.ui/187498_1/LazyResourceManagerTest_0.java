
	@SuppressWarnings("unchecked")
	public void testNullDescriptor() {
		TestResourceManager tst = new TestResourceManager();
		LazyResourceManager mgr = new LazyResourceManager(2, tst);
		ImageDescriptor nullDescriptor = null;

		AtomicReference<DeviceResourceDescriptor> expected = (AtomicReference<DeviceResourceDescriptor>) mgr
				.create(nullDescriptor);
		assertAlife(expected, mgr, tst, nullDescriptor);
		mgr.destroy(nullDescriptor);
		assertDestroyed(expected, mgr, tst, nullDescriptor);

		ResourceManager real = JFaceResources.getResources();
		mgr = new LazyResourceManager(2, real);
		Object created = mgr.createImageWithDefault(nullDescriptor);
		assertNotNull(created);
		mgr.destroy(nullDescriptor);
	}
