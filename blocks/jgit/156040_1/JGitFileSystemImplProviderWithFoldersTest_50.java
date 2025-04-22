	@Test
	public void testNewFileSystemWithSubfolder() throws IOException {
		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo)
		assertThat(stream).isEmpty();
	}

	@Test
	public void testCreateFileIntoRepositoryWithFolder() throws IOException

		final Map<String
			{
				put("init"
			}
		};

		final URI oldUri = URI.create(oldPath);
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(oldUri

		provider.setAttribute(path
		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write(("my cool content").getBytes());
		outStream.close();

		assertThat(new File(provider.getGitRepoContainerDir()

		int commitsCount = 0;
		for (RevCommit com : ((GitImpl) fs.getGit())._log().all().call()) {
			commitsCount++;
		}
	}

	@Test
	public void testExtractPathWithAuthority() throws IOException {

			{
				put("init"
			}
		});

		final URI uri = URI.create(path);
		final String extracted = provider.extractPath(uri);
		assertThat(extracted).isEqualTo("/readme.md");
	}

	@Test
	public void testComplexExtractPath() throws IOException {

		final FileSystem fs = provider.newFileSystem(newRepo

		final URI uri = URI.create(path);
		final String extracted = provider.extractPath(uri);
		assertThat(extracted).isEqualTo("/readme.md");
	}

	@Test
	public void testExtractComplexRepoName() throws IOException {
			{
				put("init"
			}
		});

		final URI uri = URI.create(path);
		final String extracted = provider.extractFSNameWithPath(uri);
		assertThat(extracted).isEqualTo("test/repo/readme.md");
	}

	@Test
	public void testExtractSimpleRepoName() {
		final URI uri = URI.create(path);
		final String extracted = provider.extractFSNameWithPath(uri);
		assertThat(extracted).isEqualTo("test/repo/readme.md");
	}

	@Test
	public void testExtractVerySimpleRepoName() {
		final URI uri = URI.create(path);
		final String extracted = provider.extractFSNameWithPath(uri);
		assertThat(extracted).isEqualTo("test/repo/readme.md");
	}
