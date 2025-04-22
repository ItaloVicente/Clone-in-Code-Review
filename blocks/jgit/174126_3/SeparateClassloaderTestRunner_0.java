			String pathSeparator = System.getProperty("path.separator");
			String[] classPathEntries = System.getProperty("java.class.path")
					.split(pathSeparator);
			URL[] urls = new URL[classPathEntries.length];
			for (int i = 0; i < classPathEntries.length; i++) {
				urls[i] = Paths.get(classPathEntries[i]).toUri().toURL();
			}
