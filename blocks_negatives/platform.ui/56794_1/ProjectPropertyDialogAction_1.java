        if (selection == null) {
			return null;
		}
        if (!(selection instanceof IAdaptable)) {
			return null;
		}
        IResource resource = ((IAdaptable) selection).getAdapter(IResource.class);
