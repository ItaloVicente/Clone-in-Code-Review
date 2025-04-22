						PullReferenceConfig config = configs.get(repository);
						if (config != null) {
							if (config.getRemote() != null) {
								pull.setRemote(config.getRemote());
							}
							if (config.getReference() != null) {
								pull.setRemoteBranchName(config.getReference());
							}
							pull.setRebase(config
									.getUpstreamConfig() == UpstreamConfig.REBASE);
						}
