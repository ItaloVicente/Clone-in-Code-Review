
					IResource resource = Adapters.getAdapter(o1, IResource.class, true);
					if (resource != null) {
						toSelect.add(resource);
					}

					IMarker marker = Adapters.getAdapter(o1, IMarker.class, true);
					if (marker != null) {
						IResource r2 = marker.getResource();
						if (r2.getType() != IResource.ROOT) {
							toSelect.add(r2);
						}
