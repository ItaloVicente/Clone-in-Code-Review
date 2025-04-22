				IResource selectedResource = null;
				if (object instanceof IResource) {
					selectedResource = (IResource) object;
				} else if (object instanceof IAdaptable) {
					selectedResource = ((IAdaptable) object).getAdapter(IResource.class);
				}
