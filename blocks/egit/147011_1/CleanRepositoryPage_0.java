						ResourcesPlugin.getWorkspace().run(pm -> {
							try {
								command.call();
							} catch (GitAPIException ex) {
								Activator.logError("cannot call clean command!", //$NON-NLS-1$
										ex);
							}
						}, null, IWorkspace.AVOID_UPDATE, null);
					} catch (CoreException e) {
