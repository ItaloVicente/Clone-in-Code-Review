			IPath gitDir = srcm.getGitDirAbsolutePath();
			if (unmapProject(tree, source))
				return true; // Error information in tree
			ISchedulingRule rule = ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(source);
			try {
				Job.getJobManager().beginRule(rule, null);
				monitor.worked(100);

				IPath relativeGitDir = gitDir.makeRelativeTo(sourceLocation);
				tree.standardMoveProject(source, description, updateFlags,
						monitor);

				IPath newGitDir = newLocation.append(relativeGitDir);
				try {
					mapProject(source, description, monitor, newGitDir);
				} catch (CoreException e) {
					tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(),
							0, CoreText.MoveDeleteHook_operationError, e));
				}
			} finally {
				Job.getJobManager().endRule(rule);
			}
			return true; // We're done with the move
		} else
			return FINISH_FOR_ME;
