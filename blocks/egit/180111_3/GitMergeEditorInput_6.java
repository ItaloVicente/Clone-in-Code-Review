				IResource resource = null;
				if (element instanceof HiddenResourceTypedElement) {
					resource = ((HiddenResourceTypedElement) element)
							.getRealFile();
				}
				if (resource == null && element instanceof IResourceProvider) {
					resource = ((IResourceProvider) element).getResource();
				}
				if (resource != null && adapter.isInstance(resource)) {
					return resource;
