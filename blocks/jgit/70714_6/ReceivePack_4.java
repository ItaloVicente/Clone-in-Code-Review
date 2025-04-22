	private void autoGc() {
		Repository repo = getRepository();
		if (!repo.getConfig().getBoolean(ConfigConstants.CONFIG_RECEIVE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOGC
			return;
		}
		repo.autoGC(NullProgressMonitor.INSTANCE);
	}

