				IRunnableWithProgress refactorOp = monitor -> {
					try {
						ResourcesPlugin.getWorkspace().run(r, ResourcesPlugin.getWorkspace().getRoot(),
								IWorkspace.AVOID_UPDATE, monitor);
					} catch (CoreException ex) {
						returnStatus = WorkbenchNavigatorPlugin.createErrorStatus(0, ex.getLocalizedMessage(), ex);
