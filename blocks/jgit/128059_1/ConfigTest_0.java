	@Test
	public void testIncludeSetValueMustNotTouchIncludedLines1()
			throws IOException
		File includedFile = tmp.newFile("included");
		String includedContent = createAllTypesSampleContent("Alice Muller"
				true
		Files.write(includedFile.toPath()

		File configFile = tmp.newFile("config");
		String content = createAllTypesSampleContent("Alice Parker"
				21
				+ pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = new FileBasedConfig(null
				FS.DETECTED);
		fbConfig.load();

		Consumer<FileBasedConfig> assertion = config -> assertAllTypesSampleContent(
				"Alice Muller"
				config
		assertion.accept(fbConfig);

		setValuesForAllTypesSampleContent("Alice Parker"
				CoreConfig.AutoCRLF.FALSE

		assertion = config -> assertAllTypesSampleContent("Alice Muller"
				10
		assertion.accept(fbConfig);

		fbConfig.save();
		assertion.accept(fbConfig);

		fbConfig = new FileBasedConfig(null
		fbConfig.load();
		assertion.accept(fbConfig);
	}

	@Test
	public void testIncludeSetValueMustNotTouchIncludedLines2()
			throws IOException
		File includedFile = tmp.newFile("included");
		String includedContent = createAllTypesSampleContent("Alice Muller"
				true
		Files.write(includedFile.toPath()

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile) + "\n"
				+ createAllTypesSampleContent("Alice Parker"
						CoreConfig.AutoCRLF.FALSE
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = new FileBasedConfig(null
				FS.DETECTED);
		fbConfig.load();

		Consumer<FileBasedConfig> assertion = config -> assertAllTypesSampleContent(
				"Alice Parker"
				config
		assertion.accept(fbConfig);

		setValuesForAllTypesSampleContent("Alice Parker"
				CoreConfig.AutoCRLF.TRUE
		assertion = config -> assertAllTypesSampleContent("Alice Parker"
				12
		assertion.accept(fbConfig);

		fbConfig.save();
		assertion.accept(fbConfig);

		fbConfig = new FileBasedConfig(null
		fbConfig.load();
		assertion.accept(fbConfig);
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

	private static void setValuesForAllTypesSampleContent(String name
			boolean fileMode
			long repositoryCacheExpireAfter
			Config config
		config.setString("user"
		config.setBoolean("core"
		config.setInt("core"
		config.setLong("core"
		config.setLong("core"
				repositoryCacheExpireAfter);
		config.setEnum("core"
		config.setString("remote"
	}

	@Test
	public void testIncludeUnsetSectionMustNotTouchIncludedLines()
			throws IOException
		File includedFile = tmp.newFile("included");
		RefSpec includedRefSpec = new RefSpec(
		String includedContent = "[remote \"origin\"]\n" + "fetch="
				+ includedRefSpec;
		Files.write(includedFile.toPath()

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile) + "\n"
				+ "[remote \"origin\"]\n" + "fetch=" + refSpec;
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = new FileBasedConfig(null
				FS.DETECTED);
		fbConfig.load();

		Consumer<FileBasedConfig> assertion = config -> {
			assertEquals(Arrays.asList(includedRefSpec
					config.getRefSpecs("remote"
		};
		assertion.accept(fbConfig);

		fbConfig.unsetSection("remote"

		assertion = config -> {
			assertEquals(Collections.singletonList(includedRefSpec)
					config.getRefSpecs("remote"
		};
		assertion.accept(fbConfig);

		fbConfig.save();
		assertion.accept(fbConfig);

		fbConfig = new FileBasedConfig(null
		fbConfig.load();
		assertion.accept(fbConfig);
	}

