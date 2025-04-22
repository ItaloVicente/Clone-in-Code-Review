				for (int i = 0; i < traversals.length; i++) {
					ResourceTraversal traversal = traversals[i];
					IResource[] resources = traversal.getResources();
					for (int j = 0; j < resources.length; j++) {
						IResource resource = resources[j];
						if (isDescendantOfRoots(resource)) {
							return true;
						}
					}
