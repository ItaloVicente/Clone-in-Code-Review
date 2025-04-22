				PullCommand pull = new Git(repository).pull();
				try {
					pull.setProgressMonitor(new EclipseGitProgressTransformer(
							mymonitor));
					pull.setTimeout(timeout);
					pullResult = pull.call();
				} catch (GitAPIException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (JGitInternalException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
