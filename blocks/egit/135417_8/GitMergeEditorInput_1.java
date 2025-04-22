				if (element instanceof IResourceProvider) {
					IResource resource = ((IResourceProvider) element)
							.getResource();
					if (adapter.isInstance(resource)) {
						return resource;
					}
