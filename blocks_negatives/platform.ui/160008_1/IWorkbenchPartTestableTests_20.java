		assertNotNull(IDE
				.openEditor(page, FileUtil.createFile("foo.txt", proj)));
		assertNotNull(IDE.openEditor(page, FileUtil.createFile(
				"foo.properties", proj)));
		assertNotNull(IDE.openEditor(page, FileUtil
				.createFile("foo.java", proj)));
		assertNotNull(IDE
				.openEditor(page, FileUtil.createFile("foo.xml", proj)));
