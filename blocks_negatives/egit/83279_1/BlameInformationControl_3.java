			IStorage storage = rev.getStorage(new NullProgressMonitor());
			IWorkbenchPage page = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			BlameOperation operation = new BlameOperation(
					revision.getRepository(), storage, path, parent,
					getShell(), page, line);
			JobUtil.scheduleUserJob(operation, UIText.ShowBlameHandler_JobName,
					JobFamilies.BLAME);
