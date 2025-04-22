					if (traversals != null) {
						for (int i = 0; i < traversals.length; i++) {
							IResource[] traversalResources = traversals[i].getResources();
							if (traversalResources != null) {
								resourcesFoundForThisSelection = true;
								if (resources == null) {
									resources = new ArrayList<>(getStructuredSelection().size());
								}
								for (int j = 0; j < traversalResources.length; j++) {
									resources.add(traversalResources[j]);
								}
