	@Test
	public void getLauncherPathFromLauncherProperty() throws Exception {
		System.setProperty("eclipse.launcher", PATH_TO_ECLIPSE_EXE);
		fileProvider.fileExistsAnswers.put(PATH_TO_ECLIPSE_EXE, true);
		fileProvider.isDirectoryAnswers.put(PATH_TO_ECLIPSE_EXE, false);

		assertEquals(PATH_TO_ECLIPSE_EXE, registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty() throws Exception {
		System.out.println("registrationWindows1: " + registrationWindows);
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", URL_TO_ECLIPSE_HOME);
		fileProvider.fileExistsAnswers.put(pathToEclipseHome, true);
		fileProvider.isDirectoryAnswers.put(pathToEclipseHome, true);
		fileProvider.newDirectoryStreamAnswers.computeIfAbsent(pathToEclipseHome, path -> new HashMap<>()).put("*.exe",
				Arrays.asList(pathToEclipseHome + "\\Eclipse.exe"));

		System.out.println("registrationWindows2: " + registrationWindows);
		assertEquals(PATH_TO_ECLIPSE_EXE, registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty_NoExeFileInDirectory() throws Exception {
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", URL_TO_ECLIPSE_HOME);
		fileProvider.fileExistsAnswers.put(pathToEclipseHome, true);
		fileProvider.isDirectoryAnswers.put(pathToEclipseHome, true);
		fileProvider.newDirectoryStreamAnswers.computeIfAbsent(pathToEclipseHome, path -> new HashMap<>()).put("*.exe",
				Collections.emptyList());

		assertNull(registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty_DirectoryDoesNotExist() throws Exception {
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", URL_TO_ECLIPSE_HOME);
		fileProvider.fileExistsAnswers.put(pathToEclipseHome, false);

		assertNull(registrationWindows.getEclipseLauncher());
	}

	@Test
	public void getLauncherPathFromEclipseHomeProperty_NotAFileUrl() throws Exception {
		System.clearProperty("eclipse.launcher");
		System.setProperty("eclipse.home.location", "http://path/to/eclipse");

		assertNull(registrationWindows.getEclipseLauncher());
	}

