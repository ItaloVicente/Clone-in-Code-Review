	@Test
	public void testCreateANewDirectoryWithMigrationEnv() throws IOException {

		final Map<String
			{
				put("init"
				put("migrate-from"
			}
		};

		final URI newUri = URI.create(newPath);
		provider.newFileSystem(newUri

		provider.getFileSystem(newUri);
		assertThat(new File(provider.getGitRepoContainerDir()
		assertThat(provider.getFileSystem(newUri)).isNotNull();
	}
