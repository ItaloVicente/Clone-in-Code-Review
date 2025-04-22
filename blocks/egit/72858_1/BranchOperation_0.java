				closeProjectsMissingAfterCheckout(progress);

				try (Git git = new Git(repository)) {
					CheckoutCommand co = git.checkout();
					co.setName(target);

					try {
						co.call();
					} catch (CheckoutConflictException e) {
						return;
					} catch (JGitInternalException e) {
						throw new CoreException(
								Activator.error(e.getMessage(), e));
					} catch (GitAPIException e) {
						throw new CoreException(
								Activator.error(e.getMessage(), e));
					} finally {
						result = co.getResult();
					}
					if (result.getStatus() == Status.NONDELETED) {
						retryDelete(result.getUndeletedList());
					}
					progress.worked(1);
					refreshAffectedProjects(progress);

					postExecute(progress.newChild(1));
				}
			}

			private void closeProjectsMissingAfterCheckout(SubMonitor progress)
					throws CoreException {
