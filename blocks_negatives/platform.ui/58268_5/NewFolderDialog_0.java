				try {
					monitor
							.beginTask(
									IDEWorkbenchMessages.NewFolderDialog_progress,
									2000);
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					if (linkTarget == null) {
						folderHandle.create(false, true, monitor);
					} else {
						folderHandle.createLink(linkTarget,
								IResource.ALLOW_MISSING_LOCAL, monitor);
					}
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
				} finally {
					monitor.done();
