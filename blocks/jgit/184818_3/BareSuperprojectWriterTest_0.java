	@Test
	public void write_commitMsgWithBody() throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			RepoProject repoProject = new RepoProject("subprojectX"
					"refs/heads/branch-x"

			RemoteReader mockRemoteReader = mock(RemoteReader.class);
					"refs/heads/branch-x"))
							.thenReturn(ObjectId.fromString(SHA1_A));

			String commitBody = "Text for the commit body\n\nTag: xxxx";
			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					BareWriterConfig.getDefault()

			RevCommit commit = w.write(Arrays.asList(repoProject));
			assertThat(commit.getFullMessage()
					is("Added repo manifest.\n\n" + commitBody + "\n"));
		}
	}

