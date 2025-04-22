	private void showAnnotations(FileDiff d) {
		try {
			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			RevCommit commit = d.getChange().equals(ChangeType.DELETE) ? d
					.getCommit().getParent(0) : d.getCommit();
			IFileRevision rev = CompareUtils.getFileRevision(d.getPath(),
					commit, getRepository(),
					d.getChange().equals(ChangeType.DELETE) ? d.getBlobs()[0]
							: d.getBlobs()[d.getBlobs().length - 1]);
			if (rev != null) {
				BlameOperation op = new BlameOperation(getRepository(),
						rev.getStorage(new NullProgressMonitor()), d.getPath(),
						commit, window.getShell(), page);
				JobUtil.scheduleUserJob(op, UIText.ShowBlameHandler_JobName,
						null);
			} else {
				String message = NLS.bind(
						UIText.CommitFileDiffViewer_notContainedInCommit,
						d.getPath(), d.getCommit().getId().getName());
				Activator.showError(message, null);
			}
		} catch (IOException e) {
			Activator.logError(UIText.GitHistoryPage_openFailed, e);
			Activator.showError(UIText.GitHistoryPage_openFailed, null);
		} catch (CoreException e) {
			Activator.logError(UIText.GitHistoryPage_openFailed, e);
			Activator.showError(UIText.GitHistoryPage_openFailed, null);
		}
	}

