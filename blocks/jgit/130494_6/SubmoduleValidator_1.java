	public static void assertValidGitModulesFile(String gitModulesContents)
			throws IOException {
		Config c = new Config();
		try {
			c.fromText(gitModulesContents);
			for (String subsection : c.getSubsections(
					ConfigConstants.CONFIG_SUBMODULE_SECTION)) {
				String url = c.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION
						subsection
				assertValidSubmoduleUri(url);

				assertValidSubmoduleName(subsection);

				String path = c.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION
						ConfigConstants.CONFIG_KEY_PATH);
				assertValidSubmodulePath(path);
			}
		} catch (ConfigInvalidException e) {
			throw new IOException(
					MessageFormat.format(
							JGitText.get().invalidGitModules
							e));
		} catch (SubmoduleValidationException e) {
			throw new IOException(e.getMessage()
		}
	}
