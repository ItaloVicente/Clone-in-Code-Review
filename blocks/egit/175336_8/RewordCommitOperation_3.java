		File gpgProgram = GitSettings.getGpgExecutable();
		GpgConfig gpgConfig = new GpgConfig(repository.getConfig()) {

			@Override
			public String getProgram() {
				return gpgProgram != null ? gpgProgram.getAbsolutePath()
						: super.getProgram();
			}
		};
