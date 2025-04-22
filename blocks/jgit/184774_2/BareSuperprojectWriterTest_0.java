	@Test
	public void write_setGitModulesAttributes() throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			Map<String
			attrs.put("a-key"
			attrs.put("-b-key"

			RepoProject repoProject = new RepoProject("subprojectX"
					SHA1_A

			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					BareWriterConfig.getDefault()

			RevCommit commit = w.write(List.of(repoProject));

			String contents = readContents(bareRepo
			Optional<String> gitModulesLine = Arrays
					.asList(contents.split("\n")).stream()
					.filter(line -> line.startsWith(".gitmodules")).findFirst();
			assertTrue(gitModulesLine.isPresent());
			assertTrue(gitModulesLine.get().contains(" a-key=a-value"));
			assertTrue(gitModulesLine.get().contains(" -b-key"));
		}
	}

