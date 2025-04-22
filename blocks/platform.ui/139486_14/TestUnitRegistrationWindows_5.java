		if (originalEclipseLauncher != null) {
			System.setProperty("eclipse.launcher", originalEclipseLauncher);
		} else {
			System.clearProperty("eclipse.launcher");
		}
		if (originalEclipseHome != null) {
			System.setProperty("eclipse.home.location", originalEclipseHome);
		} else {
			System.clearProperty("eclipse.home.location");
		}
