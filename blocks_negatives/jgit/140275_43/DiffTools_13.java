			Map<String, String> env = ExternalToolUtils.prepareEnvironment(repo,
					localFile, remoteFile, mergedFile, null);
			boolean trust = config.isTrustExitCode();
			if (trustExitCode != BooleanTriState.UNSET) {
				trust = trustExitCode == BooleanTriState.TRUE;
			}
