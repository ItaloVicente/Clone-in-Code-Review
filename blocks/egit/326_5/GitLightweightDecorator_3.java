						try {
							mapping.refreshIgnoreNode(resource);
						} catch (IOException e) {
							handleException(resource, new CoreException(new Status(IStatus.ERROR, Activator.getPluginId(),
									e.getMessage())));
						}
