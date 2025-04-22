			protected void execute(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				SubMonitor subMonitor = SubMonitor.convert(monitor, selected.length);
				if (subMonitor.isCanceled()) {
					throw new OperationCanceledException();
				}
				MultiStatus status = new MultiStatus(IDEWorkbenchPlugin.IDE_WORKBENCH, 1,
						DataTransferMessages.WizardProjectsImportPage_projectsInWorkspaceAndInvalid, null);
				for (Object element : selected) {
					status.add(createExistingProject((ProjectRecord) element, subMonitor.newChild(1)));
				}
				if (!status.isOK()) {
					throw new InvocationTargetException(new CoreException(status));
