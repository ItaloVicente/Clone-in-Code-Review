		if (object instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) object)
					.getAdapter(IResource.class);
			if (res != null && res.getType() == IResource.FILE)
				return true;
		}
