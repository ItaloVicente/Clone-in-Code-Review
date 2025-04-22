		} else {
			Job job = new WorkspaceJob(IDEWorkbenchMessages.Workspace_refreshing) {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					root.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					return Status.OK_STATUS;
				}
			};
			job.setRule(root);
			job.schedule();
		}
