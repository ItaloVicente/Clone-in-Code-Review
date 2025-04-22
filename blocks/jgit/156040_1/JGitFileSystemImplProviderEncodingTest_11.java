	private int gitDaemonPort;

	@Override
	public Map<String
		Map<String
		gitPrefs.put(GIT_DAEMON_ENABLED
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT
		return gitPrefs;
	}

	@Test
	public void test() throws IOException {

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file-name.txt"
					}
				}).execute();

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file+name.txt"
					}
				}).execute();

		new Commit(origin.getGit()
				new HashMap<String
					{
						put("file name.txt"
					}
				}).execute();


		final Map<String
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo

		assertThat(fs).isNotNull();

		fs.getPath("file+name.txt").toUri();

		provider.getPath(fs.getPath("file+name.txt").toUri());

		URI uri = fs.getPath("file+name.txt").toUri();
		Path path = provider.getPath(uri);
		Path path1 = fs.getPath("file+name.txt");
		assertThat(path).isEqualTo(path1);

		assertThat(provider.getPath(fs.getPath("file name.txt").toUri())).isEqualTo(fs.getPath("file name.txt"));

		assertThat(fs.getPath("file.txt").toUri());

		assertThat(provider.getPath(fs.getPath("file.txt").toUri())).isEqualTo(fs.getPath("file.txt"));
	}
