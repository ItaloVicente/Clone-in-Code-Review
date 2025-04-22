			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			IFileRevision rev = CompareUtils.getFileRevision(d.getNewPath(), d
					.getChange().equals(ChangeType.DELETE) ? d.getCommit()
					.getParent(0) : d.getCommit(), getRepository(), d
					.getChange().equals(ChangeType.DELETE) ? d.getBlobs()[0]
					: d.getBlobs()[d.getBlobs().length - 1]);
			if (rev != null)
