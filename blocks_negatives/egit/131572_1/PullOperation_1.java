							pull.setRebase(config.getUpstreamConfig());
						}
						MergeStrategy strategy = Activator.getDefault()
								.getPreferredMergeStrategy();
						if (strategy != null) {
							pull.setStrategy(strategy);
						}
						pullResult = pull.call();
						results.put(repository, pullResult);
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
					} finally {
						if (refreshNeeded(pullResult)) {
							ProjectUtil.refreshValidProjects(validProjects,
									progress.newChild(1,
											SubMonitor.SUPPRESS_NONE));
						} else {
							progress.worked(1);
						}
