					command.call();
					ProjectUtil.refreshResources(
									ProjectUtil.getProjectsContaining(
											repository, itemsToClean),
									subMonitor.newChild(1));
				} catch (GitAPIException | JGitInternalException
						| CoreException e) {
					throw new InvocationTargetException(e);
