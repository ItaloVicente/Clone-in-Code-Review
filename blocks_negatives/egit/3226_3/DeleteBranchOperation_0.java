				String taskName = NLS.bind(
						CoreText.DeleteBranchOperation_TaskName, branch
								.getName());
				actMonitor.beginTask(taskName, 1);
				try {
					new Git(repository).branchDelete().setBranchNames(
							branch.getName()).setForce(force).call();
					status = OK;
				} catch (NotMergedException e) {
					status = REJECTED_UNMERGED;
				} catch (CannotDeleteCurrentBranchException e) {
					status = REJECTED_CURRENT;
				} catch (JGitInternalException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
