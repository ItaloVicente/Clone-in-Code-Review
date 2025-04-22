
	private String getImplementationVersion() {
		Package pkg = getClass().getPackage();
		return (pkg == null) ? null : pkg.getImplementationVersion();
	}

	private String getBundleVersion() {
		ClassLoader cl = getClass().getClassLoader();
		if (cl instanceof URLClassLoader) {
			URL url = ((URLClassLoader) cl).findResource(JarFile.MANIFEST_NAME);
			if (url != null)
				return getBundleVersion(url);
		}
		return null;
	}

	private static String getBundleVersion(URL url) {
		try {
			InputStream is = url.openStream();
			try {
				Manifest manifest = new Manifest(is);
			} finally {
				is.close();
			}
		} catch (IOException e) {
		}
		return null;
	}
