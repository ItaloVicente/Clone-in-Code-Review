
	private String getLauncherFromLauncherProperty() {
		String launcher = System.getProperty("eclipse.launcher"); //$NON-NLS-1$
		if (launcher != null && this.fileProvider.fileExists(launcher) && !fileProvider.isDirectory(launcher)) {
			return launcher;
		}
		return null;
	}

	private String getLauncherFromHomeLocation() {
		String homeLocation = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
		Assert.isNotNull(homeLocation, "home location must not be null"); //$NON-NLS-1$

		URL homeLocationUrl;
		try {
			homeLocationUrl = new URL(homeLocation);
		} catch (MalformedURLException e) {
			return null;
		}
		if (!"file".equals(homeLocationUrl.getProtocol())) { //$NON-NLS-1$
			return null;
		}

		String directory = fileProvider.getFilePath(homeLocationUrl);
		if (!fileProvider.fileExists(directory) || !fileProvider.isDirectory(directory)) {
			return null;
		}

		try (DirectoryStream<Path> stream = fileProvider.newDirectoryStream(directory, "*.exe")) { //$NON-NLS-1$
			for (Path path : stream) {
				return path.toString();
			}
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return null;
	}
