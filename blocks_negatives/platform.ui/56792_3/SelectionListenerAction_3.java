			} else if (next instanceof IAdaptable) {
				IResource resource = ((IAdaptable) next).getAdapter(IResource.class);
				if (resource != null) {
					if (resources == null) {
						resources = new ArrayList<>(getStructuredSelection().size());
					}
					resources.add(resource);
					continue;
				}
			} else {
