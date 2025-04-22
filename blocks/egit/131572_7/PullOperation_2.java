					pull.setRebase(config.getUpstreamConfig());
				}
				MergeStrategy strategy = Activator.getDefault()
						.getPreferredMergeStrategy();
				if (strategy != null) {
					pull.setStrategy(strategy);
				}
				pullResult = pull.call();
				synchronized (results) {
					results.put(repository, pullResult);
