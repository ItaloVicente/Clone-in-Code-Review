			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				GitCompareFileRevisionEditorInput gitCompareInput = (GitCompareFileRevisionEditorInput) input;
				FileRevisionTypedElement leftRevision = gitCompareInput
						.getLeftRevision();
				IFile leftResource = (IFile) gitCompareInput
						.getAdapter(IFile.class);
				FileRevisionTypedElement rightRevision = gitCompareInput
						.getRightRevision();
				String changedFilePath = null;
				if (leftResource != null) {
					changedFilePath = leftResource
							.getProjectRelativePath()
							.toString();
				} else if (leftRevision != null) {
					changedFilePath = leftRevision.getPath();
				}
