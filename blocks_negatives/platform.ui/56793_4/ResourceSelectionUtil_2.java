            if (next instanceof IAdaptable) {
                Object resource = ((IAdaptable) next)
                        .getAdapter(IResource.class);
                if (resource == null) {
					return null;
				} else if (resourceIsType((IResource) resource, resourceMask)) {
					result.add(resource);
				}
            } else {
