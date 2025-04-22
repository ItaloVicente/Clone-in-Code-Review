	private CompletableFuture<Map<IContainer, Integer>> refreshSeverities() {
		return CompletableFuture.supplyAsync(() -> {
			Map<IContainer, Integer> severities = new HashMap<>();
			try {
				for (IMarker marker : WorkbenchNavigatorPlugin.getWorkspace().getRoot().findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE)) {
					int severity = marker.getAttribute(IMarker.SEVERITY, -1);
					IContainer container = marker.getResource().getParent();
					if (marker.getResource() instanceof IContainer) {
						container = (IContainer) marker.getResource();
					}
					while (container != null) {
						if (!severities.containsKey(container) || severities.get(container).intValue() < severity) {
							severities.put(container, Integer.valueOf(severity));
							if (container.getType() == IResource.FOLDER) {
								container = container.getParent();
							} else if (container.getType() == IResource.PROJECT) {
								container = NestedProjectManager.getInstance().getMostDirectOpenContainer((IProject)container);
							} else {
								container = null;
							}
						} else {
							container = null;
						}
					}
				}
			} catch (CoreException e) {
				WorkbenchNavigatorPlugin.log(e.getMessage(),
						new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, e.getMessage(), e));
				throw new RuntimeException(e);
			}
			return severities;
		});
