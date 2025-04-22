			IResource selectedResource = null;
			if (next instanceof IResource) {
				selectedResource = (IResource) next;
			} else if (next instanceof IAdaptable) {
				selectedResource = ((IAdaptable) next).getAdapter(IResource.class);
			}
