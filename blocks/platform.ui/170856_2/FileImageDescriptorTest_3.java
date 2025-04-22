	public void testGetxName() {
		ImageDescriptor descriptor = ImageDescriptor.createFromFile(FileImageDescriptorTest.class,
				"/icons/imagetests/zoomIn.png");
		ImageData imageData = descriptor.getImageData(100);
		assertNotNull(imageData);
		ImageData imageDataZoomed = descriptor.getImageData(200);
		assertNotNull(imageDataZoomed);
		assertEquals(imageData.width * 2, imageDataZoomed.width);
	}

	public void testGetxPath() {
		ImageDescriptor descriptor = ImageDescriptor.createFromFile(FileImageDescriptorTest.class,
				"/icons/imagetests/16x16/zoomIn.png");
		ImageData imageData = descriptor.getImageData(100);
		assertNotNull(imageData);
		ImageData imageDataZoomed = descriptor.getImageData(200);
		assertNotNull(imageDataZoomed);
		assertEquals(imageData.width * 2, imageDataZoomed.width);
	}

