		otherFolder = new File(rootDir, "other_folder");
		assertTrue(otherFolder.mkdirs());
		File otherFile = new File(otherFolder, "otherFile.txt");
		rootFile = new File(rootDir, "rootFile.txt");
		Files.write(otherFile.toPath(),
				Arrays.<String> asList("Hello", "otherFile"),
				Charset.defaultCharset());
		Files.write(rootFile.toPath(), Arrays.<String> asList("Hi", "rootFile"),
				Charset.defaultCharset());
		repository1 = new TestRepository(new File(rootDir,
