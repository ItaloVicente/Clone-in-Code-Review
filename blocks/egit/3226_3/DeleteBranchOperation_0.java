
				String taskName;
				if (branches.size() == 1)
					taskName = NLS.bind(
							CoreText.DeleteBranchOperation_TaskName, branches
									.iterator().next().getName());
				else {
					String names = ""; //$NON-NLS-1$
					for (Ref ref : branches)
						names = names + ref.getName() + ", "; //$NON-NLS-1$
					taskName = NLS.bind(
							CoreText.DeleteBranchOperation_TaskName,
							names.substring(0, names.length() - 2));
				}
				actMonitor.beginTask(taskName, branches.size());
				for (Ref branch : branches) {
					try {
						new Git(repository).branchDelete().setBranchNames(
								branch.getName()).setForce(force).call();
						status = OK;
					} catch (NotMergedException e) {
						status = REJECTED_UNMERGED;
						break;
					} catch (CannotDeleteCurrentBranchException e) {
						status = REJECTED_CURRENT;
						break;
					} catch (JGitInternalException e) {
						throw new CoreException(Activator.error(e.getMessage(), e));
					}
					actMonitor.worked(1);
