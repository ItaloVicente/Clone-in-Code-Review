		try (ZipFile zipFile = new ZipFile(filePath)) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				String name = entry.getName();
				assertTrue(name, name.startsWith(project.getName() + "/"));
			}
