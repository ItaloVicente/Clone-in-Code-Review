			    for (ResourceTraversal traversal : traversals) {
				IResource[] resources = traversal.getResources();
				for (IResource resource : resources) {
				    if (isDescendantOfRoots(resource)) {
					return true;
				    }
