		registryWriter = new RegistryWriterMock();
		fileProvider = new FileProviderMock();
		fileProvider.fileExistsAnswers.put(PATH_TO_ECLIPSE_EXE, true);
		fileProvider.isDirectoryAnswers.put(PATH_TO_ECLIPSE_EXE, false);
		fileProvider.urlTosFilePaths.put(new URL(URL_TO_ECLIPSE_HOME), PATH_TO_ECLIPSE_HOME);
		registrationWindows = new RegistrationWindows(registryWriter, fileProvider);
