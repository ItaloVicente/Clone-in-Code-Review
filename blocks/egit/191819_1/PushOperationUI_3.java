				int numberOfBranches = repository.getRefDatabase()
						.getRefsByPrefix(Constants.R_HEADS).size();
				if (numberOfBranches == 0) {
					nothingToPush(parent);
					return null;
				}
				if (numberOfBranches > 1) {
					if (!warnMatching(parent,
							RepositoryUtil.INSTANCE
									.getRepositoryName(repository),
							remoteCfg.getName(), "push.default=matching")) { //$NON-NLS-1$
