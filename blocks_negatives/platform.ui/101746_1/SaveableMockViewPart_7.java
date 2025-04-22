					ResourcesPlugin.getWorkspace().getRoot().accept(new IResourceVisitor() {

						@Override
						public boolean visit(IResource resource) {
							if (someFile[0] != null) {
								return false;
							}
							if (resource.getType() == IResource.FILE) {
								someFile[0] = (IFile) resource;
								return false;
							}
							return true;
