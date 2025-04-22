				for (int i = 0; i < repositories.length; i++) {
					Repository repository = repositories[i];
					if (mymonitor.isCanceled())
						throw new CoreException(Status.CANCEL_STATUS);
					PullCommand pull = new Git(repository).pull();
					try {
						pull
								.setProgressMonitor(new EclipseGitProgressTransformer(
										new SubProgressMonitor(mymonitor, 1)));
						pull.setTimeout(timeout);
						results.put(repository, pull.call());
					} catch (DetachedHeadException e) {
						results.put(repository, Activator.error(
								CoreText.PullOperation_DetachedHeadMessage, e));
					} catch (InvalidConfigurationException e) {
						results
								.put(
										repository,
										Activator
												.error(
														CoreText.PullOperation_PullNotConfiguredMessage,
														e));
					} catch (GitAPIException e) {
						results.put(repository, Activator.error(e.getMessage(),
								e));
					} catch (JGitInternalException e) {
						results.put(repository, Activator.error(e.getMessage(),
								e));
					} finally {
						mymonitor.worked(1);
					}
