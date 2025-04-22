			protected void execute(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				try {
					monitor.beginTask("", selected.length); //$NON-NLS-1$
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					MultiStatus status = new MultiStatus(IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
							DataTransferMessages.WizardProjectsImportPage_projectsInWorkspaceAndInvalid, null);
					for (Object element : selected) {
						status.add(createExistingProject((ProjectRecord) element, new SubProgressMonitor(monitor, 1)));
					}
					if (!status.isOK()) {
						throw new InvocationTargetException(new CoreException(status));
					}
				} finally {
					monitor.done();
