		RevCommit commit = d.getCommit().getParent(0);
		ObjectId blob = d.getBlobs()[0];
		openInEditor(d.getOldPath(), commit, blob);
	}

	private void openInEditor(String path, RevCommit commit, ObjectId blob) {
		try {
			IFileRevision rev = CompareUtils.getFileRevision(path, commit,
					getRepository(), blob);
			if (rev != null) {
				IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				EgitUiEditorUtils.openEditor(page, rev,
						new NullProgressMonitor());
			} else {
				String message = NLS.bind(
						UIText.CommitFileDiffViewer_notContainedInCommit, path,
						commit.getName());
				Activator.showError(message, null);
			}
		} catch (IOException e) {
			Activator.logError(UIText.GitHistoryPage_openFailed, e);
			Activator.showError(UIText.GitHistoryPage_openFailed, null);
		} catch (CoreException e) {
			Activator.logError(UIText.GitHistoryPage_openFailed, e);
			Activator.showError(UIText.GitHistoryPage_openFailed, null);
		}
