			RefSpec match = refSpecs.stream().filter(RefSpec::isMatching)
					.findAny().orElse(null);
			if (match != null) {
				if (repository.getRefDatabase()
							.getRefsByPrefix(Constants.R_HEADS).size() > 1) {
					if (!warnMatching(parent,
							RepositoryUtil.INSTANCE
									.getRepositoryName(repository),
							remoteCfg.getName(),
							MessageFormat.format(
									UIText.PushOperationUI_PushMatchingPushRefSpec,
									remoteCfg.getName(), match))) {
						return null;
					}
				}
			} else {
				Collection<RemoteRefUpdate> updates = Transport
						.findRemoteRefUpdatesFor(repository, refSpecs,
								remoteCfg.getFetchRefSpecs());
				if (updates.isEmpty()) {
					nothingToPush(parent);
