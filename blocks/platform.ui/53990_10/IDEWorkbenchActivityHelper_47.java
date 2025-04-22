				IResourceDelta[] children = mainDelta.getAffectedChildren();
				Set projectsToUpdate = new HashSet();
				for (int i = 0; i < children.length; i++) {
					IResourceDelta delta = children[i];
					if (delta.getResource().getType() == IResource.PROJECT) {
						IProject project = (IProject) delta.getResource();
