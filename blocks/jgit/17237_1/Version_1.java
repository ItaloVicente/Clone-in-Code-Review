
	private String getImplementationVersion() {
		Package pkg = getClass().getPackage();
		if (pkg != null) {
			return pkg.getImplementationVersion();
		}
		return null;
	}

	private String getBundleVersion() {
		ClassLoader cl = getClass().getClassLoader();
		if (cl instanceof URLClassLoader) {
			URL url = ((URLClassLoader) cl).findResource(JarFile.MANIFEST_NAME);
			try {
				Manifest manifest = new Manifest(url.openStream());
				return manifest.getMainAttributes().getValue("Bundle-Version");
			} catch (IOException e) {
			}
		}
		return null;
	}
