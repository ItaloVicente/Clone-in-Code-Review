	@Test
	public void testIncludeAbsolute()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes());
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes());
		bos.write(includedFile.getAbsolutePath()
				.replace(File.separatorChar

		final File file = createFile(bos.toByteArray());
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

	@Test
	public void testIncludeRelativeDot()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes()
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes());
		bos.write(("./" + includedFile.getName()).getBytes());

		final File file = createFile(bos.toByteArray()
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

	@Test
	public void testIncludeRelativeDotDot()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes()
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes());
		bos.write(("../" + includedFile.getParentFile().getName() + "/"
				+ includedFile.getName()).getBytes());

		final File file = createFile(bos.toByteArray()
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

	@Test
	public void testIncludeRelativeDotDotNotFound()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes());
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes());
		bos.write(("../" + includedFile.getName()).getBytes());

		final File file = createFile(bos.toByteArray());
		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(null
	}

	@Test
	public void testIncludeWithTilde()
			throws IOException
		final File includedFile = createFile(CONTENT1.getBytes()
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write("[include]\npath=".getBytes());
		bos.write(("~/" + includedFile.getName()).getBytes());

		final File file = createFile(bos.toByteArray()
		final FS fs = FS.DETECTED.newInstance();
		fs.setUserHome(includedFile.getParentFile());

		final FileBasedConfig config = new FileBasedConfig(file
		config.load();
		assertEquals(ALICE
	}

