
	private static String discoverVersion() throws IOException {
		Enumeration<URL> e;

		e = Version.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
		while (e.hasMoreElements()) {
			Manifest m = new Manifest(e.nextElement().openStream());
			Attributes a = m.getMainAttributes();
			if (a != null) {
				String bundle = a.getValue("Bundle-SymbolicName");
				if ("org.eclipse.jgit.pgm".equals(bundle)) {
					return a.getValue("Bundle-Version");
				}
			}
		}
		return null;
	}
