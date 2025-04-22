	/**
	 * @return returns the Launcher Path
	 */
	String getLauncherPath() {
		return launcherPath;
	}

	/**
	 * @param launcherPath - contains Eclipse launcher Path
	 *
	 */
	void setLauncherPath(String launcherPath) {
		this.launcherPath = launcherPath;
	}

	/**
	 * {@link URI#create(String)} and {@link URI#URI(String)} fail for paths with
	 * spaces. Fix is to use {@link File#toURI()}. The latter cannot handle the
	 * "file:/C:/..." paths returned by Eclipse, so strip file protocol first.
	 *
	 * @param path Path which needs to be converted to URI path
	 * @return returns the URI path
	 */
	static URI filePathToURI(String path) {
		try {
			return new URI(path);
		} catch (URISyntaxException e) {
				String strippedPath = stripProtocol(path);
				return new File(strippedPath).toURI();
			}
			return new File(path).toURI();
		}
	}

	/**
	 * @param path Path which needs to be changed relative to OS
	 * @return returns the changed string.
	 */
	static String stripProtocol(String path) {
		return new Path(path).setDevice(null).makeRelative().toOSString();
	}

	/**
	 * @param launcher     Launcher path to which the URI scheme is pointing to
	 * @param homeLocation Passes the home location
	 * @return Returns the launcher path
	 */
	private String getLauncher(final String launcher, final URI homeLocation) {
		if (launcher != null) {
			if (this.fileProvider.fileExists(launcher) && !fileProvider.isDirectory(launcher)) {
				return launcher;
			}
		}
		return getLauncherFromHomeLocation(homeLocation);
	}

	/**
	 * Launcher may be null in runtime workbenches hosted by PDE. Check home
	 * location for any launcher file as a fallback.
	 *
	 * @param homeLocation
	 * @return returns the launcher
	 */
	static String getLauncherFromHomeLocation(URI homeLocation) {
		String fetchedPath;
		if (homeLocation != null) {
			File homeLoc = new File(homeLocation);
			if (homeLoc.exists() && homeLoc.isDirectory()) {
				try (DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(homeLoc.toPath(), "*.exe")) { //$NON-NLS-1$
					for (java.nio.file.Path path : stream) {
						fetchedPath = path.toString();
						stream.close();
						return fetchedPath;
					}
					stream.close();
				} catch (IOException e) {
					throw new IllegalStateException(e.getMessage(), e);
				}
				File parentFile = homeLoc.getParentFile();
				if (parentFile != null) {
					return getLauncherFromHomeLocation(parentFile.toURI());
				}
			}
		}
		return null;
	}
