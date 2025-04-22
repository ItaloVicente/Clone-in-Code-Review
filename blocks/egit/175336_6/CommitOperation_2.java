		if (sign) {
			File gpg = GitSettings.getGpgExecutable();
			if (gpg != null) {
				GpgConfig cfg = new GpgConfig(repo.getConfig()) {

					@Override
					public String getProgram() {
						return gpg.getAbsolutePath();
					}
				};
				commitCommand.setGpgConfig(cfg);
			}
		}
