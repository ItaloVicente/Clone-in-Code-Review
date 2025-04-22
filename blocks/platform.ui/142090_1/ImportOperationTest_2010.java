				.forName("org.eclipse.ui.tests.datatransfer.ImportOperationTest");
		InputStream stream = testClass.getResourceAsStream("tests.ini");
		Properties properties = new Properties();
		properties.load(stream);
		localDirectory = properties.getProperty("localSource");
		setUpDirectory();
	}


	private void setUpDirectory() throws IOException {
		File rootDirectory = new File(localDirectory);
		rootDirectory.mkdir();
		localDirectory = rootDirectory.getAbsolutePath();
		for (String directoryName : directoryNames) {
			createSubDirectory(localDirectory, directoryName);
		}
	}

	@Override
