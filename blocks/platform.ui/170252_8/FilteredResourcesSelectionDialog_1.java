			final IResource resource = (IResource) element;

			if (parentIsRoot(resource)) {
				return provider.getImage(resource);
			}

			return provider.getImage(resource.getParent());
