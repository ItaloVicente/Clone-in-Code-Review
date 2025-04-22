			File gpg = GitSettings.getGpgExecutable();
			GpgConfig gpgConfig = new GpgConfig(repo.getConfig()) {

				@Override
				public String getProgram() {
					return gpg != null ? gpg.getAbsolutePath()
							: super.getProgram();
				}
			};
			if (SignatureUtils.checkSigningKey(signer, gpgConfig, tagger)) {
