					if (file != null) {
						left = new ResourceEditableRevision(rev, file,
								runnableContext);
					} else {
						left = new LocationEditableRevision(rev, location,
								runnableContext);
					}
