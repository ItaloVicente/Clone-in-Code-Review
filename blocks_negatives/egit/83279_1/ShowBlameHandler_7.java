			IStorage storage = revision.getStorage(new NullProgressMonitor());
			BlameOperation op = new BlameOperation(repo, storage, path, commit,
					HandlerUtil.getActiveShell(event), page.getSite().getPage());
			JobUtil.scheduleUserJob(op, UIText.ShowBlameHandler_JobName,
					JobFamilies.BLAME);
		} catch (IOException | CoreException e) {
