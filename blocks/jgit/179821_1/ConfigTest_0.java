	@Test
	public void testCommitTemplateConfig()
			throws ConfigInvalidException
		Config config = new Config(null);
		assertNull(config.get(CommitConfig.KEY).getCommitTemplatePath());
		assertNull(config.get(CommitConfig.KEY).getCommitTemplateContent());
		File tempFile = tmp.newFile("testCommitTemplate-");
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile
		String expectedTemplatePath = tempFile.getPath();
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		String commitEncoding = config.get(CommitConfig.KEY)
				.getCommitEncoding();
		assertEquals(expectedTemplatePath
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent());
		assertNull("no commitEncoding has been set so it must be null"
				commitEncoding);
	}

	@Test
	public void testCommitTemplateEncoding()
			throws ConfigInvalidException
		Config config = new Config(null);
		File tempFile = tmp.newFile("testCommitTemplate-");
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile
		String expectedTemplatePath = tempFile.getPath();
		config = parse("[i18n]\n\tcommitEncoding = utf-8\n"
				+ "[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent());
		String commitEncoding = config.get(CommitConfig.KEY)
				.getCommitEncoding();
		assertEquals("commitEncoding has been set to utf-8 it must be utf-8"
				"utf-8"
	}

	@Test
	public void testCommitTemplatePathInHomeDirecory()
			throws ConfigInvalidException
		Config config = new Config(null);
		File tempFile = tmp.newFile("testCommitTemplate-");
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile
		String homeDir = System.getProperty("user.home");
		File tempFileInHomeDirectory = File.createTempFile("fileInHomeFolder"
				".tmp"
		tempFileInHomeDirectory.deleteOnExit();
		JGitTestUtil.write(tempFileInHomeDirectory
		String expectedTemplatePath = tempFileInHomeDirectory.getPath()
				.replace(homeDir
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		assertEquals(expectedTemplatePath
		assertEquals(templateContent
				config.get(CommitConfig.KEY).getCommitTemplateContent());
	}

	@Test(expected = ConfigInvalidException.class)
	public void testCommitTemplateWithInvalidEncoding()
			throws ConfigInvalidException
		Config config = new Config(null);
		File tempFile = tmp.newFile("testCommitTemplate-");
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile
		config = parse("[i18n]\n\tcommitEncoding = invalidEcoding\n"
				+ "[commit]\n\ttemplate = " + tempFile.getPath() + "\n");
		config.get(CommitConfig.KEY).getCommitTemplateContent();
	}

	@Test(expected = FileNotFoundException.class)
	public void testCommitTemplateWithInvalidPath()
			throws ConfigInvalidException
		Config config = new Config(null);
		File tempFile = tmp.newFile("testCommitTemplate-");
		String templateContent = "content of the template";
		JGitTestUtil.write(tempFile
		String expectedTemplatePath = "nonExistingTemplate";
		config = parse("[commit]\n\ttemplate = " + expectedTemplatePath + "\n");
		String templatePath = config.get(CommitConfig.KEY)
				.getCommitTemplatePath();
		assertEquals(expectedTemplatePath
		config.get(CommitConfig.KEY).getCommitTemplateContent();
	}

