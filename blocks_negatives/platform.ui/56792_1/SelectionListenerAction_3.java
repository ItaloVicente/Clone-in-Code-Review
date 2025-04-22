				if (mapping != null) {
					ResourceTraversal[] traversals = null;
					try {
						traversals = mapping.getTraversals(
								ResourceMappingContext.LOCAL_CONTEXT,
								new NullProgressMonitor());
					} catch (CoreException exception) {
						IDEWorkbenchPlugin.log(exception.getLocalizedMessage(), exception.getStatus());
					}
