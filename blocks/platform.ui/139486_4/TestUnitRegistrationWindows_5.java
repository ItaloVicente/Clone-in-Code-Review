	@Test
	public void getLauncherPathFromLauncherProperty() throws Exception {
		System.setProperty("eclipse.launcher", PATH_TO_ECLIPSE_EXE);
		fileProvider.fileExistsAnswers.put(PATH_TO_ECLIPSE_EXE, true);
		fileProvider.isDirectoryAnswers.put(PATH_TO_ECLIPSE_EXE, false);

		assertEquals(PATH_TO_ECLIPSE_EXE, registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty() throws Exception {
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", URL_TO_ECLIPSE_HOME);
		fileProvider.fileExistsAnswers.put(PATH_TO_ECLIPSE_HOME, true);
		fileProvider.isDirectoryAnswers.put(PATH_TO_ECLIPSE_HOME, true);
		fileProvider.newDirectoryStreamAnswers.computeIfAbsent(PATH_TO_ECLIPSE_HOME, path -> new HashMap<>())
				.put("*.exe", Arrays.asList(PATH_TO_ECLIPSE_HOME + "\\Eclipse.exe"));

		assertEquals(PATH_TO_ECLIPSE_EXE, registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty_NoExeFileInDirectory() throws Exception {
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", URL_TO_ECLIPSE_HOME);
		fileProvider.fileExistsAnswers.put(PATH_TO_ECLIPSE_HOME, true);
		fileProvider.isDirectoryAnswers.put(PATH_TO_ECLIPSE_HOME, true);
		fileProvider.newDirectoryStreamAnswers.computeIfAbsent(PATH_TO_ECLIPSE_HOME, path -> new HashMap<>())
				.put("*.exe", Collections.emptyList());

		assertNull(registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty_DirectoryDoesNotExist() throws Exception {
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", URL_TO_ECLIPSE_HOME);
		fileProvider.fileExistsAnswers.put(PATH_TO_ECLIPSE_HOME, false);

		assertNull(registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty_NotAFileUrl() throws Exception {
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", "http://path/to/eclipse");

		assertNull(registrationWindows.getEclipseLauncher());
	}

