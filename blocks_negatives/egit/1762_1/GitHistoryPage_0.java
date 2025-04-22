		else if (o instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) o)
					.getAdapter(IResource.class);
			in = resource == null ? null : new ResourceList(
					new IResource[] { resource });
		} else
