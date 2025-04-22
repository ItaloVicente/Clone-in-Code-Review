				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) {
					try {
						op.execute(monitor);
						RevCommit newHead = op.getNewHead();
						List<Ref> revertedRefs = op.getRevertedRefs();
						if (newHead != null && revertedRefs.isEmpty())
							showRevertedDialog(shell);
						if (newHead == null)
							showFailureDialog(shell, commit,
									op.getFailingResult());
					} catch (CoreException e) {
						Activator.handleError(
								UIText.RevertOperation_InternalError, e, true);
