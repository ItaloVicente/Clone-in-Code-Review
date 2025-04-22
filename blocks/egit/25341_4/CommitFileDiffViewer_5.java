			IFileRevision rev = CompareUtils.getFileRevision(path, commit,
					getRepository(), blob);
			if (rev != null) {
				IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
