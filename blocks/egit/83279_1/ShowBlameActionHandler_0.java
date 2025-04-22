				Shell shell = HandlerUtil.getActiveShell(event);
				IWorkbenchPage page = HandlerUtil.getActiveSite(event)
						.getPage();
				JobUtil.scheduleUserJob(
						new BlameOperation(mapping.getRepository(),
								(IFile) resource, repoRelativePath, null, shell,
								page),
						UIText.ShowBlameHandler_JobName, JobFamilies.BLAME);
