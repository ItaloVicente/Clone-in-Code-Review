				IPath locationPath = new Path(fit.getEntryFile().getPath());
				final IFile file = ResourcesPlugin.getWorkspace().getRoot()
						.getFileForLocation(locationPath);
				if (file == null)
					continue;
				if (!conflicting || useWorkspace)
					rev = new LocalFileRevision(file);
				else
					rev = GitFileRevision.inCommit(repository, headCommit,
							gitPath, null);

				EditableRevision leftEditable = new EditableRevision(rev) {
					@Override
					public void setContent(final byte[] newContent) {
						try {
							run(false, false, new IRunnableWithProgress() {
								public void run(IProgressMonitor myMonitor)
										throws InvocationTargetException,
										InterruptedException {
									try {
										file.setContents(
												new ByteArrayInputStream(
														newContent), false,
												true, myMonitor);
									} catch (CoreException e) {
										throw new InvocationTargetException(e);
									}
								}
							});
						} catch (InvocationTargetException e) {
							Activator
									.handleError(e.getTargetException()
											.getMessage(), e
											.getTargetException(), true);
						} catch (InterruptedException e) {
						}
					}
				};
				try {
					leftEditable.cacheContents(monitor);
				} catch (CoreException e) {
					throw new IOException(e.getMessage());
				}
