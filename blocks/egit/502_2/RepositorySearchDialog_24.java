
							try {
								findGitDirsRecursive(file, directories,
										monitor, lookForNested);
							} catch (Exception ex) {
								Activator.getDefault().getLog().log(
										new Status(IStatus.ERROR, Activator
												.getPluginId(),
												ex.getMessage(), ex));
							}
