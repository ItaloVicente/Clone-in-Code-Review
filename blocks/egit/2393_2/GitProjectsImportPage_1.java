						ConnectProviderOperation cpo = new ConnectProviderOperation(
								project, repository.getDirectory());
						try {
							cpo.execute(new NullProgressMonitor());
						} catch (CoreException e) {
							throw new InvocationTargetException(e);
						}
