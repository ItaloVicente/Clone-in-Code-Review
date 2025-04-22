	@Test
	public void testIncludeSetValueMustNotTouchIncludedLines1()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = createAllTypesSampleContent("Alice Parker"
				21
				+ pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsIncluded(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueMustNotTouchIncludedLines2()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile) + "\n"
				+ createAllTypesSampleContent("Alice Parker"
						CoreConfig.AutoCRLF.FALSE
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsConfig(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustContainsInclude()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustEmptySection1()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[user]\n[include]\npath="
				+ pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNewWithName(config
					REFS_BACKUP);
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustEmptySection2()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile)
				+ "\n[user]";
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustExistingSection1()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[user]\nemail=alice@home\n[include]\npath="
				+ pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNewWithName(config
					REFS_BACKUP);
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustExistingSection2()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile)
				+ "\n[user]\nemail=alice@home\n";
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeUnsetSectionMustNotTouchIncludedLines()
			throws IOException
		File includedFile = tmp.newFile("included");
		RefSpec includedRefSpec = new RefSpec(REFS_UPSTREAM);
		String includedContent = "[remote \"origin\"]\n" + "fetch="
				+ includedRefSpec;
		Files.write(includedFile.toPath()

		File configFile = tmp.newFile("config");
		RefSpec refSpec = new RefSpec(REFS_ORIGIN);
		String content = "[include]\npath=" + pathToString(includedFile) + "\n"
				+ "[remote \"origin\"]\n" + "fetch=" + refSpec;
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);

		Consumer<FileBasedConfig> assertion = config -> {
			assertEquals(Arrays.asList(includedRefSpec
					config.getRefSpecs("remote"
		};
		assertion.accept(fbConfig);

		fbConfig.unsetSection("remote"
		assertValuesAsIsSaveLoad(fbConfig
			assertEquals(Collections.singletonList(includedRefSpec)
					config.getRefSpecs("remote"
		});
	}

	private File createAllTypesIncludedContent() throws IOException {
		File includedFile = tmp.newFile("included");
		String includedContent = createAllTypesSampleContent("Alice Muller"
				true
		Files.write(includedFile.toPath()
		return includedFile;
	}

	private static void assertValuesAsIsSaveLoad(FileBasedConfig fbConfig
			Consumer<FileBasedConfig> assertion)
			throws IOException
		assertion.accept(fbConfig);

		fbConfig.save();
		assertion.accept(fbConfig);

		fbConfig = loadConfig(fbConfig.getFile());
		assertion.accept(fbConfig);
	}

	private static void setAllValuesNew(Config config) {
		config.setString("user"
		config.setBoolean("core"
		config.setInt("core"
		config.setLong("core"
		config.setLong("core"
		config.setEnum("core"
		config.setString("remote"
	}

	private static void assertValuesAsIncluded(Config config
		assertAllTypesSampleContent("Alice Muller"
				CoreConfig.AutoCRLF.TRUE
	}

	private static void assertValuesAsConfig(Config config
		assertAllTypesSampleContent("Alice Parker"
				CoreConfig.AutoCRLF.FALSE
	}

	private static void assertValuesAsNew(Config config
		assertValuesAsNewWithName(config
	}

	private static void assertValuesAsNewWithName(Config config
			String... refs) {
		assertAllTypesSampleContent(name
				CoreConfig.AutoCRLF.FALSE
	}

	private static void assertSections(Config config
		assertEquals(Arrays.asList(sections)
				new ArrayList<>(config.getSections()));
	}

	private static String createAllTypesSampleContent(String name
			boolean fileMode
			long repositoryCacheExpireAfter
			String fetchRefSpec) {
		final StringBuilder builder = new StringBuilder();
		builder.append("[user]\n");
		builder.append("name=");
		builder.append(name);
		builder.append("\n");

		builder.append("[core]\n");
		builder.append("fileMode=");
		builder.append(fileMode);
		builder.append("\n");

		builder.append("deltaBaseCacheLimit=");
		builder.append(deltaBaseCacheLimit);
		builder.append("\n");

		builder.append("packedGitLimit=");
		builder.append(packedGitLimit);
		builder.append("\n");

		builder.append("repositoryCacheExpireAfter=");
		builder.append(repositoryCacheExpireAfter);
		builder.append("\n");

		builder.append("autocrlf=");
		builder.append(autoCRLF.name());
		builder.append("\n");

		builder.append("[remote \"origin\"]\n");
		builder.append("fetch=");
		builder.append(fetchRefSpec);
		builder.append("\n");
		return builder.toString();
	}

	private static void assertAllTypesSampleContent(String name
			boolean fileMode
			long repositoryCacheExpireAfter
			Config config
		assertEquals(name
		assertEquals(fileMode
				config.getBoolean("core"
		assertEquals(deltaBaseCacheLimit
				config.getInt("core"
		assertEquals(packedGitLimit
				config.getLong("core"
		assertEquals(repositoryCacheExpireAfter
				null
		assertEquals(autoCRLF
				CoreConfig.AutoCRLF.INPUT));
		final List<RefSpec> refspecs = new ArrayList<>();
		for (String fetchRefSpec : fetchRefSpecs) {
			refspecs.add(new RefSpec(fetchRefSpec));
		}

		assertEquals(refspecs
	}

