				String repoName = Activator.getDefault().getRepositoryUtil()
						.getRepositoryName(gfRepo.getRepository());
				if (!UIRepositoryUtils.handleUncommittedFiles(
						gfRepo.getRepository(), shell,
						MessageFormat.format(
								UIText.FeatureCheckoutHandler_cleanupDialog_title,
								repoName))) {
