			if (ok) {
				if (fullNewName.startsWith(Constants.R_HEADS)) {
					String shortOldName = fullOldName
							.substring(Constants.R_HEADS.length());
					final StoredConfig repoConfig = repo.getConfig();
					String oldRemote = repoConfig.getString(
