			if (fullNewName.startsWith(Constants.R_HEADS)) {
				String shortOldName = fullOldName.substring(Constants.R_HEADS
						.length());
				final StoredConfig repoConfig = repo.getConfig();
				for (String name : repoConfig.getNames(
						ConfigConstants.CONFIG_BRANCH_SECTION
					String[] values = repoConfig.getStringList(
