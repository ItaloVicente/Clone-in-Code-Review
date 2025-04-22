				gpgConfig = new GpgConfig(repository.getConfig()) {

					@Override
					public String getProgram() {
						return gpg != null ? gpg.getAbsolutePath()
								: super.getProgram();
					}
				};
