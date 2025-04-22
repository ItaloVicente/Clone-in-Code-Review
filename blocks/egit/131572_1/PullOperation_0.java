						Job pullJob = Job.create(getPullTaskName(repository, config), monitor -> {
							PullCommand pull = git.pull();
							PullResult pullResult = null;
							try {
								pull.setProgressMonitor(new EclipseGitProgressTransformer(
														monitor));
								pull.setTimeout(timeout);
								pull.setCredentialsProvider(credentialsProvider);
								if (config != null) {
									if (config.getRemote() != null) {
										pull.setRemote(config.getRemote());
									}
									if (config.getReference() != null) {
										pull.setRemoteBranchName(config.getReference());
									}
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
								}
							} catch (DetachedHeadException e) {
								results.put(repository, Activator.error(
										CoreText.PullOperation_DetachedHeadMessage, e));
							} catch (InvalidConfigurationException e) {
								IStatus error = Activator
										.error(CoreText.PullOperation_PullNotConfiguredMessage,
												e);
								results.put(repository, error);
							} catch (GitAPIException e) {
								results.put(repository,
										Activator.error(e.getMessage(), e));
							} catch (JGitInternalException e) {
								Throwable cause = e.getCause();
								if (cause == null || !(cause instanceof TransportException))
									cause = e;
								results.put(repository,
										Activator.error(cause.getMessage(), cause));
