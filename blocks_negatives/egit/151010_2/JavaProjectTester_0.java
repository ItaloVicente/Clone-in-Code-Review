				for (int i = 0; i < MAX_DELETE_RETRY; i++) {
					try {
						project.delete(IResource.FORCE
								| IResource.ALWAYS_DELETE_PROJECT_CONTENT,
								null);
						break;
					} catch (CoreException e) {
						if (i == MAX_DELETE_RETRY - 1) {
							throw e;
						}
						try {
							Activator.logInfo(
									"Sleep before retrying to delete project "
											+ project.getLocationURI());
							Thread.sleep(DELETE_RETRY_DELAY);
						} catch (InterruptedException e1) {
						}
					}
				}

