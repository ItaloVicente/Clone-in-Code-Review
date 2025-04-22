							IFile[] files = ResourcesPlugin.getWorkspace()
									.getRoot()
									.findFilesForLocationURI(file.toURI());
							for (int i = 0; i < files.length; i++)
								files[i].refreshLocal(IResource.DEPTH_ZERO,
										monitor);
						} else
