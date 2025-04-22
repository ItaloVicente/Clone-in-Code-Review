				final IStatus[] errorStatus = new IStatus[1];
				errorStatus[0] = Status.OK_STATUS;
				final WorkspaceModifyOperation op = (WorkspaceModifyOperation) createOperation(errorStatus);
				WorkspaceJob job = new WorkspaceJob("refresh") { //$NON-NLS-1$

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						try {
							op.run(monitor);
							Shell shell = provider.getShell();
