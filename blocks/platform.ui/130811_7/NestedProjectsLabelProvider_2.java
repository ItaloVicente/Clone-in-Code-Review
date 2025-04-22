				for (IMarker marker : WorkbenchNavigatorPlugin.getWorkspace().getRoot().findMarkers(IMarker.PROBLEM,
						true, IResource.DEPTH_INFINITE)) {
					IResource resource = marker.getResource();
					if (currentDirty == null || currentDirty.contains(resource)) {
						int severity = marker.getAttribute(IMarker.SEVERITY, 0);
						if (severity >= 0) {
							propagateSeverityToCache(resource, severity);
