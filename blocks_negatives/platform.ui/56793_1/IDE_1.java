			Object resource = null;
			if (next instanceof IResource) {
				resource = next;
			} else if (next instanceof IAdaptable) {
				resource = ((IAdaptable) next).getAdapter(IResource.class);
			}
