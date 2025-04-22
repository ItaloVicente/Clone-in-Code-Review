					if (config.getReference() != null) {
						pull.setRemoteBranchName(config.getReference());
					}
					pull.setRebase(config.getUpstreamConfig());
				}
				MergeStrategy strategy = Activator.getDefault()
						.getPreferredMergeStrategy();
				if (strategy != null) {
					pull.setStrategy(strategy);
