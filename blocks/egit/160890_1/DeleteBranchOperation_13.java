				taskName = NLS.bind(
						CoreText.DeleteBranchOperation_TaskName, names);
			}
			SubMonitor progress = SubMonitor.convert(actMonitor, taskName,
					branches.size());
			for (Ref branch : branches) {
				if (progress.isCanceled()) {
					throw new OperationCanceledException(
							CoreText.DeleteBranchOperation_Canceled);
				}
				try (Git git = new Git(repository)) {
					git.branchDelete().setBranchNames(
							branch.getName()).setForce(force).call();
					status = OK;
				} catch (NotMergedException e1) {
					status = REJECTED_UNMERGED;
					break;
				} catch (CannotDeleteCurrentBranchException e2) {
					status = REJECTED_CURRENT;
					break;
				} catch (JGitInternalException | GitAPIException e3) {
					throw new CoreException(Activator.error(e3.getMessage(), e3));
