		if (selected instanceof IAdaptable)
			return CommonUtils.getAdapter(((IAdaptable) selected), IResource.class);

		if (selected instanceof IResource)
			return (IResource) selected;

		return null;
