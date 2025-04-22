	@Test
	public void write_setExtraContents() throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			RepoProject repoProject = new RepoProject("subprojectX"
					"refs/heads/branch-x"

			RemoteReader mockRemoteReader = mock(RemoteReader.class);
					"refs/heads/branch-x"))
							.thenReturn(ObjectId.fromString(SHA1_A));

			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					BareWriterConfig.getDefault()
					List.of(new BareSuperprojectWriter.ExtraContent("x"
							"extra-content")));

			RevCommit commit = w.write(Arrays.asList(repoProject));

			String contents = readContents(bareRepo
			assertThat(contents
		}
	}

