		super.setUp();
		TestDescriptor.refCount = 0;
		Display display = Display.getCurrent();
		globalResourceManager = new DeviceResourceManager(display);
		testImage = getImage("icons/anything.gif").createImage(display);
		testImage2 = getImage("icons/binary_co.gif").createImage(display);
		testColor = new Color(display, new RGB(10, 40, 20));
		testColor2 = new Color(display, new RGB(230, 100, 26));

		descriptors = new DeviceResourceDescriptor[] {
				new TestDescriptor(getImage("icons/anything.gif")),
				new TestDescriptor(getImage("icons/anything.gif")),
				new TestDescriptor(getImage("icons/binary_co.gif")),
				new TestDescriptor(getImage("icons/binary_co.gif")),
				new TestDescriptor(getImage("icons/mockeditorpart1.gif")),

				new TestDescriptor(getImage("icons/view.gif")), // 5
				new TestDescriptor(ImageDescriptor.createFromImage(testImage2)),
				new TestDescriptor(ImageDescriptor.createFromImage(testImage)),
				new TestDescriptor(ImageDescriptor.createFromImage(testImage)),
				new TestDescriptor(ImageDescriptor.createFromImage(testImage, display)),

				new TestDescriptor(ImageDescriptor.createFromImage(testImage, display)),  // 10
				new TestDescriptor(ImageDescriptor.createFromImage(testImage2, display)),
				new TestDescriptor(ColorDescriptor.createFrom(new RGB(10, 200, 54))),
				new TestDescriptor(ColorDescriptor.createFrom(new RGB(10, 200, 54))),
				new TestDescriptor(ColorDescriptor.createFrom(new RGB(200, 220, 54))),

				new TestDescriptor(ColorDescriptor.createFrom(testColor)), // 15
				new TestDescriptor(ColorDescriptor.createFrom(testColor)),
				new TestDescriptor(ColorDescriptor.createFrom(testColor2)),
				new TestDescriptor(ColorDescriptor.createFrom(testColor, display)),
				new TestDescriptor(ColorDescriptor.createFrom(testColor, display)),
				new TestDescriptor(ColorDescriptor.createFrom(testColor2, display))
				};

		numDupes = 11;
	}

	@Override
