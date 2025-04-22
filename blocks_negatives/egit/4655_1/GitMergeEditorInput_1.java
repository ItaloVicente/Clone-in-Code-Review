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
