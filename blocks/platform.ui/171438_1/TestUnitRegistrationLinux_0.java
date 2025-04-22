	@Test
	public void returnsFallbackIfEclipseLauncherIsNotSet() throws Exception {
		System.clearProperty(ECLIPSE_LAUNCHER);
		String eclipseLauncher = registration.getEclipseLauncher();
		assertEquals("/home/myuser/Eclipse/" + "eclipse", eclipseLauncher);
	}

