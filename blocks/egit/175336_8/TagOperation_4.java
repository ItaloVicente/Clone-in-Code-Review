			File gpg = GitSettings.getGpgExecutable();
			if (gpg != null) {
				GpgConfig cfg = new GpgConfig(repository.getConfig()) {

					@Override
					public String getProgram() {
						return gpg.getAbsolutePath();
					}
				};
				command.setGpgConfig(cfg);
			}
