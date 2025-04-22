			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					op.execute(monitor);
					RevCommit newHead = op.getNewHead();
					List<Ref> revertedRefs = op.getRevertedRefs();
					if (newHead != null && revertedRefs.isEmpty())
						showRevertedDialog(shell);
					if (newHead == null) {
						RevCommit newestUnmergedCommit = null;
						for (RevCommit commit : commits) {
							if (!contains(revertedRefs, commit)) {
								newestUnmergedCommit = commit;
								break;
							}
						}
						showFailureDialog(shell, newestUnmergedCommit,
								op.getFailingResult());
