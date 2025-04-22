							progress.setWorkRemaining(files.length);
							for (IFile f : files) {
								f.refreshLocal(IResource.DEPTH_ZERO,
										progress.newChild(1));
							}
						} else {
