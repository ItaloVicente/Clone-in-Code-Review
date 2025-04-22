			if (resourceAdapter instanceof IContributorResourceAdapter) {
				IResource resource = ((IContributorResourceAdapter) resourceAdapter).getAdaptedResource(element);
				if (resource != null) {
					ResourceMapping mapping = internalGetAdapter(resource, ResourceMapping.class);
					if (mapping != null) {
						return mapping;
					}
