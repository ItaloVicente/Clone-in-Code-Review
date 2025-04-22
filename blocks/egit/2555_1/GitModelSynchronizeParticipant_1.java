		if (object instanceof GitModelBlob || object instanceof IFile)
			return true;
		if (object instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) object)
					.getAdapter(IResource.class);
			if (res != null && res.getType() == IResource.FILE)
				return true;
		}
		return false;
