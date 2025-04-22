		assertEquals("utf-8"
	}

	@Test
	void testCommitTemplateWithInvalidEncoding() {
		assertThrows(ConfigInvalidException.class
			Config config = new Config(null);
			File workTree = newFolder(tmp
			File tempFile = File.createTempFile("testCommitTemplate-"
			Repository repo = FileRepositoryBuilder.create(workTree);
			String templateContent = "content of the template";
			JGitTestUtil.write(tempFile
			config = parse("[i18n]\n\tcommitEncoding = invalidEcoding\n"
					+ "[commit]\n\ttemplate = "
					+ Config.escapeValue(tempFile.getPath()) + "\n");
			config.get(CommitConfig.KEY).getCommitTemplateContent(repo);
		});
