							if (isSymLink) {
								item.addContentChangeListener(source -> {
									try {
										item.commit(null);
									} catch (CoreException e) {
										Activator.handleStatus(e.getStatus(),
												true);
									}
								});
							}
