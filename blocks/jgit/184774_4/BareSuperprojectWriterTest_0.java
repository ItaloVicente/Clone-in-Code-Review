	@Test
	public void write_recordSubmoduleLabels() throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			RepoProject repoProject = new RepoProject("subprojectX"
					SHA1_A

			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					BareWriterConfig.getDefault()

			RevCommit commit = w.write(Arrays.asList(repoProject));

			String contents = readContents(bareRepo
			Optional<String> subprojectXLine = Arrays
					.asList(contents.split("\n")).stream()
					.filter(line -> line.startsWith("/path/to ")).findFirst();
			assertTrue(subprojectXLine.isPresent());
			assertTrue(subprojectXLine.get().contains(" groupA"));
			assertTrue(subprojectXLine.get().contains(" groupB"));
		}
	}

	@Test
	public void write_doNotRecordSubmoduleLabels_noExtraAttr()
			throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			RepoProject repoProject = new RepoProject("subprojectX"
					SHA1_A

			BareWriterConfig cfg = BareWriterConfig.getDefault();
			cfg.recordSubmoduleLabels = false;

			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					cfg

			RevCommit commit = w.write(Arrays.asList(repoProject));

			String idStr = commit.getId().name() + ":.gitattributes";
			ObjectId modId = bareRepo.resolve(idStr);
			assertTrue(modId == null);
		}
	}

	@Test
	public void write_doNotRecordSubmoduleLabels_withExtraAttr()
			throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			RepoProject repoProject = new RepoProject("subprojectX"
					SHA1_A

			BareWriterConfig cfg = BareWriterConfig.getDefault();
			cfg.recordSubmoduleLabels = false;

			Map<String
			gitModulesAttr.put("a-key"

			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					BareWriterConfig.getDefault()

			RevCommit commit = w.write(Arrays.asList(repoProject));

			String contents = readContents(bareRepo
			Optional<String> gitModulesLine = Arrays
					.asList(contents.split("\n")).stream()
					.filter(line -> line.startsWith("path/to ")).findFirst();
			assertFalse(gitModulesLine.isPresent());
		}
	}

	@Test
	public void write_setGitModulesAttributes()
			throws Exception {
		try (Repository bareRepo = createBareRepository()) {
			RepoProject repoProject = new RepoProject("subprojectX"
					SHA1_A

			Map<String
			attr.put("a-key"
			attr.put("-b-key"

			BareSuperprojectWriter w = new BareSuperprojectWriter(bareRepo
					null
					BareWriterConfig.getDefault()

			RevCommit commit = w.write(Arrays.asList(repoProject));

			String contents = readContents(bareRepo
			Optional<String> gitModulesLine = Arrays
					.asList(contents.split("\n")).stream()
					.filter(line -> line.startsWith(".gitmodules")).findFirst();
			assertTrue(gitModulesLine.isPresent());
			assertTrue(gitModulesLine.get().contains(" a-key=a-value"));
			assertTrue(gitModulesLine.get().contains(" -b-key"));
		}
	}

