			for (String subsection : c.getSubsections(
					ConfigConstants.CONFIG_SUBMODULE_SECTION)) {
				String url = c.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION,
						subsection, ConfigConstants.CONFIG_KEY_URL);
				assertValidSubmoduleUri(url);

