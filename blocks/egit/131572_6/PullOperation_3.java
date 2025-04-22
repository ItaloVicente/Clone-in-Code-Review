				pullResult = pull.call();
				synchronized (results) {
					results.put(repository, pullResult);
				}
				IProject[] projects = ProjectUtil
						.getValidOpenProjects(repository);
				if (refreshNeeded(pullResult)) {
					ProjectUtil.refreshValidProjects(projects, monitor);
				}
				monitor.newChild(1);
				return Status.OK_STATUS;
			} catch (DetachedHeadException e) {
				return Activator.error(
						CoreText.PullOperation_DetachedHeadMessage, e);
			} catch (InvalidConfigurationException e) {
				return Activator
						.error(CoreText.PullOperation_PullNotConfiguredMessage,
								e);
			} catch (GitAPIException | CoreException e) {
				return Activator.error(e.getMessage(), e);
			} catch (JGitInternalException e) {
				Throwable cause = e.getCause();
				if (cause == null || !(cause instanceof TransportException))
					cause = e;
				return Activator.error(cause.getMessage(), cause);
			} finally {
				mymonitor.done();
