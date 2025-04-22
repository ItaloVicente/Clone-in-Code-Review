			Map<String, String> env = ExternalToolUtils.prepareEnvironment(db,
					localFile, remoteFile, mergedFile, null);
			boolean trust = config.isTrustExitCode();
			if (trustExitCode.isConfigured()) {
				trust = trustExitCode.toBoolean();
			}
