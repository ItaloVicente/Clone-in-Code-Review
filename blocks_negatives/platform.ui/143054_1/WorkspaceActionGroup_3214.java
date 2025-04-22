        		final IStatus[] errorStatus = new IStatus[1];
        		errorStatus[0] = Status.OK_STATUS;
        		final WorkspaceModifyOperation op = (WorkspaceModifyOperation) createOperation(errorStatus);

        			@Override
					public IStatus runInWorkspace(IProgressMonitor monitor)
        					throws CoreException {
        				try {
        					op.run(monitor);
        					Shell shell = provider.getShell();
