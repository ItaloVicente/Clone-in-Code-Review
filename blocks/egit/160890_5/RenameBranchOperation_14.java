		IWorkspaceRunnable action = actMonitor -> {
			String taskName = NLS.bind(
					CoreText.RenameBranchOperation_TaskName, branch
							.getName(), newName);
			SubMonitor progress = SubMonitor.convert(actMonitor);
			progress.setTaskName(taskName);
			try (Git git = new Git(repository)) {
				git.branchRename().setOldName(
						branch.getName()).setNewName(newName).call();
			} catch (JGitInternalException | GitAPIException e) {
				throw new CoreException(Activator.error(e.getMessage(), e));
