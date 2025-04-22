		this.registryWriter = new RegistryWriterMock();
		this.fileProvider = new FileProviderMock();
		this.fileProvider.fileExistsAnswers.put(PATH_TO_ECLIPSE_EXE, true);
		this.fileProvider.isDirectoryAnswers.put(PATH_TO_ECLIPSE_EXE, false);
		this.fileProvider.urlTosFilePaths.put(new URL(URL_TO_ECLIPSE_HOME), PATH_TO_ECLIPSE_HOME);
		this.registrationWindows = new RegistrationWindows(this.registryWriter, this.fileProvider);
