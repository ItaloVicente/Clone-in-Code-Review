		ZipFile zipFile = new ZipFile(filePath);
		Enumeration entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String name = entry.getName();
			assertTrue(name, name.startsWith(project.getName() + "/"));
