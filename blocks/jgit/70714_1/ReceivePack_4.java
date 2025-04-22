			autoGc();
		}
	}

	private void autoGc() {
		Repository repo = getRepository();
		if (!repo.getConfig().getBoolean(ConfigConstants.CONFIG_RECEIVE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOGC
			return;
		}
		try (Git git = Git.wrap(repo)) {
			git.gc().setAuto(true).call();
		} catch (GitAPIException e) {
			LOG.error(e.getMessage()
