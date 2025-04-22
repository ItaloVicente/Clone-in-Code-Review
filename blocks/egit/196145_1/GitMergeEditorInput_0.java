					if (isSymLink) {
						left = new LocationEditableRevision(rev, location,
								runnableContext);
					} else {
						IFile rsc = file != null ? file
								: createHiddenResource(
										location.toFile().toURI(),
										tw.getNameString(), null);
						assert rsc != null;
						left = new ResourceEditableRevision(rev, rsc,
								runnableContext);
					}
