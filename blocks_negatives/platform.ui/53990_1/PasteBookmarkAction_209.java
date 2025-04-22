            ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
                @Override
				public void run(IProgressMonitor monitor) throws CoreException {
					for (int i = 0; i < markerData.length; i++) {
						if (!markerData[i].getType().equals(IMarker.BOOKMARK)) {
							continue;
						}
						newMarkerResources.add(markerData[i].getResource());
						newMarkerAttributes.add(markerData[i].getAttributes());
