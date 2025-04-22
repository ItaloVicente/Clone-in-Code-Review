			File gpg = GitSettings.getGpgExecutable();
			GpgConfig gpgConfig;
			if (repository != null) {
				gpgConfig = new GpgConfig(repository.getConfig()) {

					@Override
					public String getProgram() {
						return gpg != null ? gpg.getAbsolutePath()
								: super.getProgram();
					}
				};
			} else {
				gpgConfig = new GpgConfig(null, GpgFormat.OPENPGP,
						gpg != null ? gpg.getAbsolutePath() : null);
			}
