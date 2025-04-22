				for (IMarker marker : WorkbenchNavigatorPlugin.getWorkspace().getRoot().findMarkers(IMarker.PROBLEM,
						true, IResource.DEPTH_INFINITE)) {
					IResource resource = marker.getResource();
					if (dirty == null || dirty.contains(resource)) {
						IContainer container = marker.getResource().getParent();
						if (marker.getResource() instanceof IContainer) {
							container = (IContainer) marker.getResource();
						}
						int severity = marker.getAttribute(IMarker.SEVERITY, -1);
						while (container != null) {
							if (!severities.containsKey(container) || severities.get(container).intValue() < severity) {
								severities.put(container, Integer.valueOf(severity));
								if (container.getType() == IResource.FOLDER) {
									container = container.getParent();
								} else if (container.getType() == IResource.PROJECT) {
									container = NestedProjectManager.getInstance()
											.getMostDirectOpenContainer((IProject) container);
								} else {
									container = null;
								}
